package com.springboot.environment.dao;

import com.springboot.environment.bean.MData;
import com.springboot.environment.repositoiry.MDataRepositoiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;

@Component
@Repository
public interface MDataDao extends JpaRepository<MData,Integer>, MDataRepositoiry {
    @Query(value = "select *from mdata d where d.data_id=?1",nativeQuery = true)
    List<MData> getAllByData_id(String data_id);

    /**
     * 特定时间段内的实时数据条数
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select count(*) from (select m.data_time from mdata m where m.station_id = ?1 and m.data_time between ?2 and ?3 group by m.data_time) mdg", nativeQuery = true)
    int  querymDataNumBetween(String stationId, String startDate, String endDate);

    /**
     * 查询最新实时数据
     * @param stationId
     * @return
     */
    @Query(value = "select * from mdata m where m.data_time = (select MAX(md.data_time) from mdata md where md.station_id = ?1) and m.station_id = ?1", nativeQuery = true)
    List<MData> queryMaxTimeMdataByStationId(String stationId);


    /**
     * 查询实时数据表中指定站点的数据条数
     * @param stationId
     * @return
     */
    @Query(value = "select count(*) from mdata m where m.station_id = ?1", nativeQuery = true)
    int querymDataNumByStationId(String stationId);


    /**
     * 该方法只是向redis写入数据使用，线上不允许使用此sql
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from mdata m where m.data_time between ?1 and ?2", nativeQuery = true)
    List<MData> getMdataByDay(String startTime, String endTime);


}
