package com.springboot.environment.dao;

import com.springboot.environment.bean.DData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface DDataDao extends JpaRepository<DData,Integer> {
    @Query(value = "select *from ddata d where d.data_id=?1",nativeQuery = true)
    List<DData> getAllByData_id(String data_id);

    @Query(value = "select * from ddata d where d.station_id=?1 and DATE_FORMAT(d.data_time,'%Y-%m')=?2",nativeQuery = true)
    List<DData> getByStationAndMonth(String station_id,String month);

    @Query(value = "select * from ddata d where d.station_id=?1 and DATE_FORMAT(d.data_time,'%Y-%m-%d')=?2",nativeQuery = true)
    List<DData> getByStationAndDate(String station_id,String date);


    /**
     * 根据站点id查询最新的日数据信息
     * @param stationId
     * @return
     */
    @Query(value = "select * from ddata d where d.data_time = (select MAX(dd.data_time) from ddata dd where dd.station_id = ?1) and d.station_id = ?1", nativeQuery = true)
    List<DData> queryMaxTimeDdataByStationId(String stationId);


    /**
     * 查询表中是否有指定站点的日数据
     */
    @Query(value = "select count(*) from ddata d where d.station_id = ?1", nativeQuery = true)
    int queryDdataNumByStationId(String stationId);



    @Query(value = "select * from station;", nativeQuery = true)
    List<DData> getByStationAndDay(String station_id,String date);


    /**
     * 查询指定站点和指定月份的日数据
     * @param stationId
     * @param monthBeginTIme
     * @param monthEndTime
     * @return
     */
    @Query(value = "select * from ddata d where d.station_id = ?1 and d.data_time between ?2 and ?3", nativeQuery = true)
    List<DData> queryDdataByStationIdAndTime(String stationId, String monthBeginTIme, String monthEndTime);


}
