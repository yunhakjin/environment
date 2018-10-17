package com.springboot.environment.dao;

import com.springboot.environment.bean.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface StationDao extends JpaRepository<Station, Integer> {

//    /**
//     * 查询所有的站点信息，返回list
//     * @return
//     */
//    List<Station> findAll();


    //站点的单维度查询

    /**
     * 根据站点对内编号查询站点信息
     * @param stationId
     * @return
     */
    Station findByStationId(int stationId);

    /**
     * 根据站点对外编号查询站点信息
     * @param stationCode
     * @return
     */
    Station findByStationCode(String stationCode);

    /**
     * 根据站点名称查询站点信息
     * @param stationName
     * @return
     */
    Station findByStationName(String stationName);

    /**
     * 根据站点状态查询所有站点信息，返回list
     * @param stationStatus
     * @return
     */
    List<Station> findByStationStatus(int stationStatus);

    /**
     * 根据站点在线标识查询站点信息，返回list
     * @param onlineFlag
     * @return
     */
    List<Station> findByOnlineFlag(int onlineFlag);

    /**
     * 通过行政区查询站点信息，枚举
     * @param district
     * @return
     */
    List<Station> findByDistrict(String district);

    /**
     * 根据街道查询站点信息，返回list
     * @param street
     * @return
     */
    List<Station> findByStreet(String street);

    /**
     * 根据国控查询站点信息
     * @param isCountryCon
     * @return
     */
    List<Station> findByCountryCon(int isCountryCon);

    /**
     * 根据市控查询站点信息
     * @param isCityCon
     * @return
     */
    List<Station> findByCityCon(int isCityCon);

    /**
     * 根据区控查询站点信息
     * @param isDomainCon
     * @return
     */
    List<Station> findByDomainCon(int isDomainCon);

    /**
     * 根据区域环境查询站点信息
     * @param area
     * @return
     */
    List<Station> findByArea(int area);

    /**
     * 根据功能区查询站点信息
     * @param domain
     * @return
     */
    List<Station> findByDomain(int domain);

    /**
     * 站点名的模糊查询
     * @param stationName
     * @return
     */
//    @Transactional
//    @Modifying
//    @Query(value = "select s from station s where s.STATION_NAME like '%?1%'")
//    List<Station> findByStationNameLike(String stationName);

}
