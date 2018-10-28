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

    @Query(value="select * from ddata d where d.station_id=?1 and d.data_time between ?2 and ?3 and data_check=?4 and data_status=?5",nativeQuery = true)
    Page<DData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, Pageable pageable);

    @Query(value = "select * from ddata d where d.station_id=?1 and DATE_FORMAT(d.data_time,'%Y-%m')=?2",nativeQuery = true)
    List<DData> getByStationAndMonth(String station_id,String month);

    @Query(value = "select * from ddata d where d.station_id=?1 and DATE_FORMAT(d.data_time,'%Y-%m-%d')=?2",nativeQuery = true)
    List<DData> getByStationAndDay(String station_id,String date);
}
