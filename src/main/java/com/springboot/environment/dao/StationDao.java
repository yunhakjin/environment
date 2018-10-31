package com.springboot.environment.dao;

import com.springboot.environment.bean.MData;
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
    Station findByStationId(String stationId);

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
    @Query(value = "select * from station s where s.STATION_NAME like %?1%", nativeQuery = true)
    List<Station> findByStationNameLike(String stationName);

    /**
     * 站点所属街道的模糊查询
     * @param street
     * @return
     */
    @Query(value = "select * from station s where s.STREET like %?1%", nativeQuery = true)
    List<Station> findByStreetLike(String street);


    /**
     * 根据站点对内信息删除站点
     * @param stationId
     */
    @Transactional
    @Modifying
    @Query(value = "delete from station where STATION_ID = ?1 ", nativeQuery = true)
    void deleteByStationId(String stationId);

    /**
     * 查询当天有数据的站点的信息
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select * from mdata m where m.data_time between ?1 and ?2 group by m.station_id asc ", nativeQuery = true)
    List<MData> queryStationNumByMdata(String startDate, String endDate);


    /**
     * 根据环境区域查询站点个数
     * @param area
     * @return
     */
    @Query(value = "select count(*) from station s where s.AREA = ?1", nativeQuery = true)
    int queryStationsNumByArea(int area);

    /**
     * 根据环境分页查询站点的信息
     * @param area
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select * from station s where s.AREA = ?1 order by s.station_id asc limit ?2,?3", nativeQuery = true)
    List<Station> queryStationsByAreaAndPage(int area, int start, int end);


    /**
     * 所有站点信息的分页查询
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select * from station s order by s.station_id asc limit ?1,?2", nativeQuery = true)
    List<Station> queryStationsByPage(int start, int end);

    /**
     * 查询所有站点的个数
     * @return
     */
    @Query(value = "select count(*) from station s", nativeQuery = true)
    int queryAllStationNum();

    /**
     * 根据站点id或name模糊查询
     * @param key
     * @return
     */
    @Query(value = "select * from station s where s.station_id like '%?1%' or s.station_name like '%?1%'", nativeQuery = true)
    List<Station> findStationsByIdAndNameLike(String key);


}
