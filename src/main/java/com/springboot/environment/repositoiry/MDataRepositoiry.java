package com.springboot.environment.repositoiry;

import com.springboot.environment.bean.MData;

import java.util.List;

public interface MDataRepositoiry {

    /**
     * 根据站点Id 开始时间和结束时间查询站点信息
     * @param statonId
     * @param startTime
     * @param endTime
     * @return
     */
    List<MData> queryMdataByStationIdAndTime(String statonId, String startTime, String endTime);


    /**
     * 根据站点id和时间拆查询实时数据
     * @param station_id
     * @param date
     * @return
     */
    List<MData> getByStationAndHour(String station_id, String date);

}
