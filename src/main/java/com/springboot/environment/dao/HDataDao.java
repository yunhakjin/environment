package com.springboot.environment.dao;

import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.MData;
import com.springboot.environment.repositoiry.HDataRepositority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Component
@Repository
public interface HDataDao extends JpaRepository<HData,Integer>, HDataRepositority {


    @Query(value = "select *from hdata d where d.data_id=?1",nativeQuery = true)
    List<HData> getAllByData_id(String data_id);

    /**
     * 查询指定时间内的数据条数
     * 直接查询主表
     * @param stationId
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select count(*) from (select h.data_time from hdata h where h.station_id = ?1 and h.data_time between ?2 and ?3 group by h.data_time) hdg", nativeQuery = true)
    int queryhDataNumBetween(String stationId, String startDate, String endDate);


    /**
     * 查询最新时间的小时数据
     * @param stationId
     * @return
     */
    @Query(value = "select * from hdata h where h.data_time = (select MAX(hd.data_time) from hdata hd where hd.station_id = ?1) and h.station_id = ?1", nativeQuery = true)
    List<HData> queryMaxTimeHdataByStationId(String stationId);



    /**
     * 查询数据最新时间
     * @param stationCode
     * @return
     */
    @Query(value = "select max(data_time) from `hdata` where station_id=?1", nativeQuery = true)
    String getLatestTimeByStationCode(String stationCode);

    /**
     * 查询最新时间的小时数据
     * @param stationCode
     * @return
     */
    @Query(value = "select * from `hdata` where data_time = (select max(data_time) from `hdata` where station_id= ?1) and station_id=?1", nativeQuery = true)
    List<HData> getLatestStationListByStationCode(String stationCode);
}
