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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WarningServiceImp implements WarningService {

    @Autowired
    private WarningDao warningDao;

    @Override
    public Page<Warning> getAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return warningDao.findAll(pageable);
    }

    @Override
    public String queryWarningByDomainAndTimeAndDistrictAndStation(String warning_district,int warning_domain, String station_id,String start_time, String end_time) {

            JSONArray warningArray = new JSONArray();
            JSONObject warningData = new JSONObject();
            JSONObject dataJson = new JSONObject();

            List<Warning> warnings = warningDao.queryWarningByDomainAndTimeAndDistrictAndStation(warning_district,warning_domain,start_time,end_time, station_id);


            if (!StringUtil.isNullOrEmpty(warnings)) {

                for (Warning warning : warnings) {
                    JSONObject warningJSON = new JSONObject();
                    warningJSON.put("station_name",warning.getStation_name());
                    warningJSON.put("norm_code",warning.getNorm_code());
                    warningJSON.put("threshold",warning.getLimit_value());
                    warningJSON.put("warning_start_time",warning.getWarning_start_time());
                    warningJSON.put("warning_end_time",warning.getWarning_end_time());
                    warningJSON.put("leq",warning.getReal_value());
                    warningJSON.put("lmx",warning.getLmx());
                    warningJSON.put("sd",warning.getSd());
                    warningJSON.put("cal",warning.getCal());
                    warningJSON.put("vdr",warning.getVdr());

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


}
