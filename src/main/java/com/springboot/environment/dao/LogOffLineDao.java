package com.springboot.environment.dao;

import com.springboot.environment.bean.LogOffLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by yww on 2018/12/28.
 */
@Component
@Repository
public interface LogOffLineDao extends JpaRepository<LogOffLine,String> {

    @Query(value = "select * from log_off_line t where begin_time=(select  max(begin_time) from log_off_line g where g.station_id=?1) and t.station_id=?1",nativeQuery = true)
    LogOffLine findByStationOrGatherID(String stationCode);


}
