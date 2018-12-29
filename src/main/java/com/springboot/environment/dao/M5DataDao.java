package com.springboot.environment.dao;

import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface M5DataDao extends JpaRepository<M5Data,Integer> {
    @Query(value = "select *from m5data d where d.data_id=?1",nativeQuery = true)
    List<M5Data> getAllByData_id(String data_id);
    /**
     * 根据站点id和指定时间查询5分钟数据
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from m5data m where m.station_id = ?1 and m.data_time between ?2 and ?3", nativeQuery = true)
    List<M5Data> queryMdataByStationIdAndTime(String stationId, String startTime, String endTime);

    @Query(value = "select * from m5data m where m.station_id = ?1 and DATE_FORMAT(m.data_time,'%Y-%m-%d %H')=?2", nativeQuery = true)
    List<M5Data> getByStationAndHour(String station_id, String time1);


}
