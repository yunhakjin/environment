package com.springboot.environment.dao;

import com.springboot.environment.bean.Norm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
/*关于指标的DAO*/
public interface NormDao extends JpaRepository<Norm,String> {
    @Query(value = "select * from norm where mflag=1",nativeQuery = true)
    public List<Norm> getAllByMflag();

    @Query(value = "select * from norm where m5flag=1",nativeQuery = true)
    public List<Norm> getAllByM5flag();

    @Query(value = "select * from norm where hflag=1",nativeQuery = true)
    public List<Norm> getAllByHflag();

    @Query(value = "select * from norm where dflag=1",nativeQuery = true)
    public List<Norm> getAllByDflag();

    @Query(value = "select * from norm where monthflag=1",nativeQuery = true)
    public List<Norm> getAllByMonthflag();

    @Query(value = "select * from norm where overflag=1",nativeQuery = true)
    public List<Norm> getAllByOverflag();

    @Query(value = "select norm_code from norm where mflag=1 and norm_name = 'LeqA'",nativeQuery = true)
    Object getLeqACode();

    @Query(value = "select * from norm where norm_id_code = ?1", nativeQuery = true)
    Norm getNormCodeByNorimIdCode(String normIdCode);

}
