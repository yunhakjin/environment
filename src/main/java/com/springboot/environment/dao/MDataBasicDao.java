package com.springboot.environment.dao;

import com.springboot.environment.bean.MDataBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface MDataBasicDao extends JpaRepository<MDataBasic,Integer> {

    @Query(value = "select count(*) from tb_data_basic_info tb where tb.STATION_ID = ?1 and tb.DATA_TIME between ?2 and ?3", nativeQuery = true)
    int getMDataCountByStationIdAndTime(String stationId, String startTime, String endTime);

    @Query(value = "select tb.DATA_TIME, tb.DATA from tb_data_basic_info tb where tb.STATION_ID = ?1 and tb.DATA_TIME between ?2 and ?3", nativeQuery = true)
    List<MDataBasic> getMdataByStationIdAndTime(String stationID, String startTime, String endTime);
}
