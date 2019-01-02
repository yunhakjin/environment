package com.springboot.environment.dao;

import com.springboot.environment.bean.M5Data;
import com.springboot.environment.repositoiry.M5DataRepositoiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface M5DataDao extends JpaRepository<M5Data,Integer>, M5DataRepositoiry {

    @Query(value = "select *from m5data d where d.data_id=?1",nativeQuery = true)
    List<M5Data> getAllByData_id(String data_id);

}
