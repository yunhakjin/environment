package com.springboot.environment.repositoiry;

import com.springboot.environment.bean.HData;

import java.util.List;

public interface HDataRepositority {


    /**
     * 查询某一个指标的数据
     * 需要分表操作
     * @param stationId
     * @param startTime
     * @param endTime
     * @param normCode
     * @return
     */
    List<HData> getNormHdataByStationIdAndTime(String stationId, String startTime, String endTime, String normCode);

    /**
     * 需要用分表操作
     * 时间格式2018-12-30 ~ 2018-12-31
     * @param station_id
     * @param date
     * @return
     */
    List<HData> getByStationAndDate(String station_id, String date);

    /**
     * 查询指定站点指定时间的小时数据，时间格式2018-12-30 ~ 2018-12-31
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    List<HData> queryHdataByStationIdAndTime(String stationId, String startTime, String endTime);
}
