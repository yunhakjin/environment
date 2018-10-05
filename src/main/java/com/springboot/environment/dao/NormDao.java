package com.springboot.environment.dao;

import com.springboot.environment.bean.Norm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
/*关于指标的DAO*/
public interface NormDao extends JpaRepository<Norm,String> {
}
