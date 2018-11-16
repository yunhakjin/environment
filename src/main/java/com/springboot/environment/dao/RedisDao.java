package com.springboot.environment.dao;

import java.util.Set;

public interface RedisDao {

    /**
     * 查询redis取得指定时间和站点的实时数据
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    Set<String> getRedisMdataByStationIdAndTimeRange(String stationId, final long startTime, final long endTime);
}
