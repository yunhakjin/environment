package com.springboot.environment.dao;

import com.springboot.environment.bean.GatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface GatherDataDao extends JpaRepository<GatherData,Integer> {
    @Query(value = "select distinct * from gatherdata g where g.gather_id=?1",nativeQuery = true)
    public List<GatherData> getAllByGather_id(String GatherId);

    @Query(value = "select distinct * from gatherdata g where g.gather_id=?1 and DATE_FORMAT(g.data_time,'%Y-%m-%d')=?2",nativeQuery = true)
    public List<GatherData> getAllByGather_idAndData_time(String gather_id,String data_time);

    @Query(value = "select * from `gatherdata` where data_time = (select max(data_time) from `gatherdata` where gather_id= ?1) and gather_id=?1", nativeQuery = true)
    GatherData getLaestDataByGather_id(String gather_id);
}
