package com.springboot.environment.dao;


import com.springboot.environment.bean.Gather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface GatherDao extends JpaRepository<Gather,Integer> {
    @Query(value = "select distinct * from gather where gather_id=?1",nativeQuery = true)
    public Gather getAllByGather_id(String gather_id);
}
