package com.springboot.environment;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.MData;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnvironmentApplication.class)
public class EnvironmentApplicationTests {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MDataDao mDataDao;

    @Test
    public void testRedis(){

        ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
        //设置redis的过期时间
        keyValue.set("test", "我能输出就表示测试成功");
        redisTemplate.expire("test", 120, TimeUnit.SECONDS);
        String key = keyValue.get("test");
        System.out.println(key);

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        ops.put("stationId", "300010", "stationData");
        ops.put("stationId", "300011", "300011stationData");
        redisTemplate.expire("stationId", 120, TimeUnit.SECONDS);
        redisTemplate.expire("300010", 60, TimeUnit.SECONDS);
        System.out.println(ops.entries("stationId").toString());

        /**
         * 测试有序集合
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTimeStamp = new Date().getTime();
        System.out.println("当前系统时间 = " +nowTimeStamp +  "     " + dateFormat.format(nowTimeStamp));

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add("300010","300010的当前的数据", 1542367018678.0);
        System.out.println("一小时之前的时间" + dateFormat.format(1542367018678.0 - 1000 * 60 * 60));
        zset.add("300010", "300010的一分钟之前的数据", 1542367018678.0 - 1000 * 60);
        System.out.println("时间戳的初始时间" + 0L);
        zset.add("300010", "系统初始时间的数据", 0L);

        System.out.println("指定时间戳内的所有数据" + zset.reverseRangeByScore("300010", nowTimeStamp - 1000 * 60 * 60, nowTimeStamp));
        System.out.println("当前时间" + nowTimeStamp);
        System.out.println("当前一小时之前的时间" + (nowTimeStamp - 1000 * 60 * 60));

        redisTemplate.expire("300010", 120, TimeUnit.SECONDS);


    }

    @Test
    public void storeDataIntoRedis(){

        long start = System.currentTimeMillis();
        List<MData> allMdata = mDataDao.getMdataByDay("2018-11-16 00:00:00", "2018-11-16 23:59:59");

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        for (MData mData : allMdata){

            String stationId = mData.getStation_id();
            double score = (double)mData.getData_time().getTime();

            JSONObject mdataJson = new JSONObject();
            mdataJson.put("data_id", mData.getData_id());
            mdataJson.put("data_time", mData.getData_time());
            mdataJson.put("station_id", mData.getStation_id());
            mdataJson.put("norm_code", mData.getNorm_code());
            mdataJson.put("norm_val", mData.getNorm_val());
            String dataString = mdataJson.toJSONString();

            zset.add(stationId, dataString, score);
        }

        System.out.println("当前耗时" + (int)(System.currentTimeMillis() - start) / 1000 );
    }


}
