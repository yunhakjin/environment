package com.springboot.environment.service;
import com.springboot.environment.bean.HData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HDataService {
    public List<HData> getAll();
    public Page<HData> getAllPage(int page, int size);
    public List<HData> getHDataByData_id(String Data_id);
    public Page<HData> getByStationAndTime(String station_id,String starttime,String endtime,int data_check,int data_status,int page,int size);

    String queryHdataByStationIdAndDatetime(String stationId, String date);
}

