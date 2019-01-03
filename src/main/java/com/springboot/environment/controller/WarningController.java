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
    public void getNewWarning() {
        int newNum = warningService.getCount();
        int lastNum = GetWarningLastNum.LastWarningNum;

        if ( newNum > lastNum) {
            int tmp = lastNum;
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

                SMSManage.getInstance().send(warningMessage,managerTel);
            }
//            return ConvertToJsonUtil.warningListConvertToJson(newWarnings);

        }
        else
//            return null;
            System.out.println("没有新的报警信息");

    }
}
