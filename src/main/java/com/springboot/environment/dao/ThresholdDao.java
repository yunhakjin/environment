package com.springboot.environment.dao;

import com.springboot.environment.bean.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
public interface ThresholdDao extends JpaRepository<Threshold,Integer> {
    @Query(value="select distinct * from threshold where target_domain=?1 and norm_code=?2",nativeQuery = true)
    Threshold getThresholdByDomain(int target_domain, String norm_code);

    @Transactional
    @Modifying
    @Query(value = "insert into threshold(target_street,target_area,target_domain,manager,norm_code,d_limit," +
            "n_limit) values(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
    void insertThreshold(String target_street,int target_area,int target_domain,
                                int manager,String norm_code,String d_limit,String n_limit);

    @Transactional
    @Modifying
    @Query(value="update threshold set target_street=?1,target_area=?2,target_domain=?3,manager=?4," +
            "norm_code=?5,d_limit=?6,n_limit=?7 where id=?8",nativeQuery = true)
    void updateThreshold(String target_street,int target_area,int target_domain,
                                int manager,String norm_code,String d_limit,String n_limit,int id);

    @Transactional
    @Modifying
    @Query(value = "delete from threshold where id=?1",nativeQuery = true)
    void deleteThreshold(int id);

    /**
     * 根据站点code查询站点所属功能区的阈值
     * @param stationCode
     * @return
     */
    @Query(value = "select t.* from threshold t, station s where t.target_domain = s.DOMAIN and s.STATION_CODE = ?1", nativeQuery = true)
    Threshold getThresholdByStationCode(String stationCode);
}
