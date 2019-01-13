package com.springboot.environment.dao;

import com.springboot.environment.bean.Warning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sts on 2018/11/26.
 */

/*关于警报的DAO*/
@Component
@Repository
public interface WarningDao extends JpaRepository<Warning,String> {

    /**
     * 查询指定功能区与时间段超标数据
     * @param warningDistrict
     * @param warningDomain
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select * from warning  where warning_domain = ?2 and warning_district = ?1 and station_id = ?5 and warning_start_time between ?3 and ?4 ", nativeQuery = true)
    List<Warning> queryWarningByDomainAndTimeAndDistrictAndStation(String warningDistrict, int warningDomain, String startTime, String endTime, String station_id);



    /**
     * 查询新插入报警
     * @param lastNum
     */
    @Query(value = "select * from warning w where w.warning_id > ?1 ",nativeQuery = true)
    List<Warning> queryNewWarning(int lastNum);

    /**
     * 获得最大id
     */
    @Query(value = "select count(*) from warning  ",nativeQuery = true)
    int getCount();

    /**
     * 获取managertel
     * @param lastNum
     * @return
     */
    @Query(value = "select manager_tel from warning where warning_id > ?1",nativeQuery = true)
    List<Warning> queryManagerTel(int lastNum);

    /**
     * 获取最新一小时报警数据
     * @param hourEnd
     * @param hourBegin
     * @return
     */
    @Query(value = "select * from warning where warning_start_time >?1 and warning_start_time <= ?2",nativeQuery = true)
    List<Warning> queryRealWarning(String hourBegin, String hourEnd);
}