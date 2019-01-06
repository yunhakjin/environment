package com.springboot.environment.serviceImpl;

/**
 * Created by sts on 2018/11/26.
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.service.WarningService;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.WarningDao;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class WarningServiceImp implements WarningService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WarningDao warningDao;

    @Override
    public String queryWarningByDomainAndTimeAndDistrictAndStation(String warning_district,int warning_domain, String start_time, String end_time,String station_id) {

            JSONArray warningArray = new JSONArray();
            JSONObject warningData = new JSONObject();
            JSONObject dataJson = new JSONObject();

            List<Warning> warnings = warningDao.queryWarningByDomainAndTimeAndDistrictAndStation(warning_district,warning_domain,start_time,end_time, station_id);

            System.out.println(warnings.size());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");

            if (warnings.size() > 0) {

                for (Warning warning : warnings) {
                    JSONObject warningJSON = new JSONObject();
                    warningJSON.put("station_name",warning.getStation_name());
                    warningJSON.put("norm_code",warning.getNorm_code());
                    warningJSON.put("threshold",warning.getLimit_value());
                    warningJSON.put("warning_start_time",sdf.format(warning.getWarning_start_time()));
                    warningJSON.put("warning_end_time",sdf.format(warning.getWarning_end_time()));
                    warningJSON.put("leq",warning.getReal_value());
                    warningJSON.put("lmx",warning.getLmx());
                    warningJSON.put("sd",warning.getSd());
                    warningJSON.put("cal",warning.getCal());
                    warningJSON.put("vdr",warning.getVdr());

                    System.out.print(warningJSON);

                    warningArray.add(warningJSON);

                }
                warningData.put("count",warnings.size());
                warningData.put("data",warningArray);

                dataJson.put("warningData",warningData);
                return dataJson.toJSONString();

            }
            else {
                warningData.put("count",warnings.size());
                warningData.put("data",warningArray);

                dataJson.put("warningData",warningData);
                return dataJson.toJSONString();
            }
        }

    @Override
    public List<Warning> queryNewWarning(int lastNum ) {
        return warningDao.queryNewWarning(lastNum);
    }

    @Override
    public int getCount() {
        return warningDao.getCount();
    }

    @Override
    public String getRedisWarning() {
        JSONObject dataJson = new JSONObject();
        //JSONArray dataArray = new JSONArray();

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<String> maxScoreWdata = zSetOperations.reverseRange("realwarningdata" ,0, 0);
        if (!maxScoreWdata.isEmpty()) {
            JSONObject realWarningJson = JSONObject.parseObject(maxScoreWdata.iterator().next());
            dataJson.put("realWarning", realWarningJson);
            return realWarningJson.toJSONString();
        }
        else
            return null;
    }


}
