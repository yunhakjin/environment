package com.springboot.environment.service;

import com.springboot.environment.bean.Warning;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * Created by sts on 2018/11/25.
 */


public interface WarningService {
    public Page<Warning> getAllPage(int page, int size);
    public String queryWarningByDomainAndTimeAndDistrictAndStation(String warning_district, int warning_domain, String start_time, String end_time,String station_id) throws ParseException;
    public List<Warning> queryNewWarning(int lastNum);
    public int getCount();
}
