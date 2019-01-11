package com.springboot.environment.controller;

/**
 * Created by sts on 2018/12/6.
 */

import com.springboot.environment.bean.Warning;
import com.springboot.environment.dao.WarningDao;
import com.springboot.environment.service.WarningService;
import com.springboot.environment.util.ConvertToJsonUtil;
import com.springboot.environment.util.SMSManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springboot.environment.util.GetWarningLastNum;
import com.springboot.environment.util.SmsClientSend;


import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/warning")
@Api("数据类api")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @Autowired
    WarningDao warningDao;

    @ApiOperation(value = "查询指定行政区，功能区，时间段的报警信息", notes = "需要定义行政区，功能区，开始时间，结束时间")
    @ApiImplicitParam(name = "params", value = "包含行政区，功能区，开始时间，结束时间的json", dataType = "JSON")
    @RequestMapping(value = "/queryWarningByDomainAndTimeAndDistrictAndStation", method = RequestMethod.POST)
    public String getWarningByDomainAndTimeAndDistrictAndStation(@RequestBody Map<String, Object> params) throws ParseException {
        System.out.println(params.toString());
        //SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd HH:00:00");

        String warning_district = params.get("district_name").toString();
        int warning_domain = (Integer) params.get("domain_id");
        String station_id = params.get("station_id").toString();
        String start_time = params.get("requestStartTime").toString();
        String end_time = params.get("requestEndTime").toString();
//        int pageSize = (Integer) params.get("each_page_num");
//        int pageNum = (Integer) params.get("current_page");

        return warningService.queryWarningByDomainAndTimeAndDistrictAndStation(warning_district, warning_domain, start_time, end_time, station_id);
    }

    @ApiOperation(value = "查询实时报警信息", notes = "")
    @RequestMapping(value = "/queryNewWarning", method = RequestMethod.GET)
    public String getNewWarning() throws ParseException {
        return warningService.getRedisWarning();
    }
}
