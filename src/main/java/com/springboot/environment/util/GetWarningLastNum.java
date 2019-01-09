package com.springboot.environment.util;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Warning;
import com.springboot.environment.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.List;

/**
 * Created by sts on 2018/12/30.
 */

@Component
public class GetWarningLastNum implements CommandLineRunner{


    @Autowired
    private WarningService warningService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static int LastWarningNum;

    public static void setLastWarningNum(int newWarningNum) { LastWarningNum = newWarningNum; }
    //public int getLastWarningNum() { return LastWarningNum; }

    @Override
    public void run(String... strings) throws Exception {

        LastWarningNum = warningService.getCount();

        final long timeInterval = 1200000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int newNum = warningService.getCount();
                    ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                    if ( newNum > LastWarningNum ) {

                        int tmp = LastWarningNum;
                        GetWarningLastNum.setLastWarningNum(newNum);
                        System.out.println(GetWarningLastNum.LastWarningNum);
                        List<Warning> newWarnings = warningService.queryNewWarning(tmp);

                        for(Warning warning : newWarnings)
                        {
                            String managerTel = warning.getManger_tel();
                            String warningMessage = "你好请前往报警站点： " + warning.getStation_name() + "\n" + "所属行政区：" + warning.getWarning_district()+ "\n" +
                                    "功能区：" + warning.getWarning_domain() + "\n" + "报警指标：" + warning.getNorm_code() + "\n" + "报警阈值：" + "\n" +
                                    warning.getLimit_value() + "\n" + "leq：" + warning.getReal_value() + "\n" + "报警开始时间" + warning.getWarning_start_time() + "【上海】";
                            System.out.println(warningMessage);
//                            SMSManage.getInstance().send(warningMessage,managerTel);

                            double score ;
                            JSONObject realwarningdataJson = new JSONObject();
                            realwarningdataJson.put("warning_id", warning.getWarning_id());
                            realwarningdataJson.put("warning_start_time", warning.getWarning_start_time().toString());
                            realwarningdataJson.put("warning_end_time", warning.getWarning_end_time().toString());
                            realwarningdataJson.put("norm_code", warning.getNorm_code());
                            realwarningdataJson.put("leq", warning.getReal_value());
                            realwarningdataJson.put("threshold",warning.getLimit_value());
                            realwarningdataJson.put("station_id",warning.getStation_id());
                            String dataString = realwarningdataJson.toJSONString();

//                            zset.add("realwarningdata",dataString,1);//score 不懂？！
                        }

                    }
                    else{
                        System.out.println("没有新的报警信息");
                        //zset.remove("realwarningdata");
                    }
                    try {
                        Thread.sleep(timeInterval);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();







}
}
