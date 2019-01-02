package com.springboot.environment.repositoiry;

import com.springboot.environment.bean.M5Data;

import java.util.List;

public interface M5DataRepositoiry {

    /**
     * 根据站点id和指定时间查询5分钟数据
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    List<M5Data> queryMdataByStationIdAndTime(String stationId, String startTime, String endTime);

    List<M5Data> getByStationAndHour(String station_id, String time1);
}
