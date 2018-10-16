package com.springboot.environment.dao;

import com.springboot.environment.bean.MData;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Component
@Repository
public interface MDataDao extends JpaRepository<MData,Integer> {
//    public Page<MData> getAllByStation_idIn(Collection<String> station_id,int page,int size);
}
