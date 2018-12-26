package com.springboot.environment.service;
import com.springboot.environment.bean.HData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HDataService {
    public List<HData> getAll();
    public Page<HData> getAllPage(int page, int size);
    public List<HData> getHDataByData_id(String Data_id);
    public List<HData> getByStationAndTime(String station_id,String starttime,String endtime);

    String queryHdataByStationIdAndDatetime(String stationId, String date, int offset);

    public List<HData> getByStationAndDate(String station_id,String date);

    Map getStationsDataByDays(Map<String, Object> params);
}

