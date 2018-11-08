package com.springboot.environment.dao;

import com.springboot.environment.bean.HData;
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
public interface HDataDao extends JpaRepository<HData,Integer> {
    @Query(value = "select *from hdata d where d.data_id=?1",nativeQuery = true)
    List<HData> getAllByData_id(String data_id);

    @Query(value="select * from hdata d where d.station_id=?1 and d.data_time between ?2 and ?3 ",nativeQuery = true)
    List<HData> getByStationAndTime(String station_id, String starttime, String endtime);

    /**
     * 查询数据表中是否有指定站点的数据
     * @param stationId
     * @return
     */
    @Query(value = "select count(*) from hdata h where h.station_id = ?1", nativeQuery = true)
    int queryHdataNumByStationId(String stationId);


    /**
     * 查询指定时间内的数据条数
     * @param stationId
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select count(*) from (select * from hdata h where h.station_id = ?1 and h.data_time between ?2 and ?3 group by h.data_time asc ) hdg", nativeQuery = true)
    int queryhDataNumBetween(String stationId, String startDate, String endDate);


    /**
     * 查询最新时间的小时数据
     * @param stationId
     * @return
     */
    @Query(value = "select * from hdata h where h.data_time = (select MAX(hd.data_time) from hdata hd where hd.station_id = ?1) and h.station_id = ?1", nativeQuery = true)
    List<HData> queryMaxTimeHdataByStationId(String stationId);

    @Query(value = "select * from hdata d where d.station_id=?1 and DATE_FORMAT(d.data_time,'%Y-%m-%d')=?2",nativeQuery = true)
    List<HData> getByStationAndDate(String station_id,String date);

    /**
     * 查询指定站点指定时间的小时数据
     * @param stationId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from hdata h where h.station_id = ?1 and h.data_time between ?2 and ?3", nativeQuery = true)
    List<HData> queryHdataByStationIdAndTime(String stationId, String startTime, String endTime);


}
