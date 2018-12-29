package com.springboot.environment.service;

import com.springboot.environment.bean.DData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DDataService {
    public List<DData> getAll();
    public List<DData> getDDataByData_id(String Data_id);
    public void saveDData(DData dData);
    public void delDData(String Data_id);
    public List<DData> getByStationAndDate(String station_id,String date);

    /**
     * 日数据查询，以月份为单位
     * @param stationId
     * @param date
     * @param offset
     * @return
     */
    String queryDdataByStationIdAndDatetime(String stationId, String date, int offset);

    public List<DData> getByStationAndMonth(String station_id,String month);
    public List<DData> getByStationAndDay(String station_id,String date);

    Map getStationsData(Map<String, Object> params);
}

