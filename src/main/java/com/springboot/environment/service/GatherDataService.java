package com.springboot.environment.service;

import com.springboot.environment.bean.GatherData;

import java.util.List;

public interface GatherDataService {
    public List<GatherData> getAllByGather_id(String GatherId);
    public List<GatherData> getAllByGather_idAndData_time(String GatherId,String Data_time);
}
