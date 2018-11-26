package com.springboot.environment.dao;

import com.springboot.environment.bean.DData;
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
public interface MDataDao extends JpaRepository<MData,Integer> {
    @Query(value = "select *from mdata d where d.data_id=?1",nativeQuery = true)
    List<MData> getAllByData_id(String data_id);

    @Query(value="select * from mdata d where d.station_id=?1 and d.data_time between ?2 and ?3 and data_check=?4 and data_status=?5",nativeQuery = true)
    Page<MData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, Pageable pageable);

    /**
     * 特定时间段内的实时数据条数
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select count(*) from (select * from mdata m where m.station_id = ?1 and m.data_time between ?2 and ?3 group by m.data_time asc ) mdg", nativeQuery = true)
    int querymDataNumBetween(String stationId, String startDate, String endDate);

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
     * 根据站点id和指定时间查询实时数据
     * @param statonId
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from mdata m where m.station_id = ?1 and m.data_time between ?2 and ?3", nativeQuery = true)
    List<MData> queryMdataByStationIdAndTime(String statonId, String startTime, String endTime);


    @Query(value = "select * from mdata m where m.station_id = ?1 and DATE_FORMAT(m.data_time,'%Y-%m-%d %H')=?2", nativeQuery = true)
    List<MData> getByStationAndHour(String station_id, String date);

    /**
     * 该方法只是向redis写入数据使用，线上不允许使用此sql
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from mdata m where m.data_time between ?1 and ?2", nativeQuery = true)
    List<MData> getMdataByDay(String startTime, String endTime);

  /*  @Query(value = "select * from `mdata` where data_time = (select max(data_time) from `mdata` where station_id= ?1) and station_id=?1", nativeQuery = true)
    List<MData> getLatestStationListByStationCode(String stationCode);


    @Query(value = "select max(data_time) from `mdata` where station_id=?1", nativeQuery = true)
    Object getLatestTimeByStationCode(String stationCode);*/
}
