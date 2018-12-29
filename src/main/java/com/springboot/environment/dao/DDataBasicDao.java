package com.springboot.environment.dao;


import com.springboot.environment.bean.DDataBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface DDataBasicDao extends JpaRepository<DDataBasic,Integer> {

    /**
     * 查询实时数据基表
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select tb. from tb_ddata_basic_info tb where  tb.STATION_ID = ?1 and tb.DATA_TIME between ?2 and ?3", nativeQuery = true)
    List<DDataBasic> getDDataBasicByStationIdAndTime(String stationId, String startTime, String endTime);

    @Query(value = "select count(*) from tb_ddata_basic_info tb where  tb.STATION_ID = ?1 and tb.DATA_TIME between ?2 and ?3", nativeQuery = true)
    int getDDataCountByStationIdAndTime(String stationId, String startTime, String endTime);
}
