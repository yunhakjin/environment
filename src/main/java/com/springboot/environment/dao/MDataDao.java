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
}
