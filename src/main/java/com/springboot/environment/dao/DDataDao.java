package com.springboot.environment.dao;

import com.springboot.environment.bean.DData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DDataDao extends JpaRepository<DData,Integer> {
    public List<DData> getDDataByData_id(String Data_id);
}
