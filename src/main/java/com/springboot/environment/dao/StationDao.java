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

    //站点的单维度查询

    /**
     * 根据站点对内编号查询站点信息
     * @param stationId
     * @return
     */
    @Query(value = "select * from station where station_code=?1",nativeQuery = true)
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
    @Query(value = "select * from station s where (s.station_code like %?1% or s.station_name like %?1%)", nativeQuery = true)
    List<Station> findByStationNameLike(String stationName);

    /**
     * 站点code的模糊查询
     * @param station_code
     * @return
     */
    @Query(value = "select * from station s where s.station_code like %?1%",nativeQuery = true)
    List<Station> finByStationCodeLike(String station_code);

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
    @Query(value = "select * from station where district =?1",nativeQuery = true)
    List<Station> getAreasByAreasName(Object o);

    @Query(value = "select * from station where station_code =?1",nativeQuery = true)
    Station findStationByStationId(String station_id);

    @Query(value = "select distinct domain from station ",nativeQuery = true)
    List<String> getFuncCodes();
    @Query(value = "select * from station s where s.STATION_ID like %?1% or s.STATION_NAME like %?1%", nativeQuery = true)
    List<Station> queryStationsByKey(String key);

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
    @Query(value = "select * from station s where s.station_code like %?1% or s.station_name like %?1%", nativeQuery = true)
    List<Station> findStationsByIdAndNameLike(String key);

    /*返回所有的街道*/
    @Query(value="select distinct s.street from station s group by s.street",nativeQuery = true)
    List<String> getAllStreet();

    /*新增某一个站点*/
    @Modifying
    @Transactional
    @Query(value="insert into station(application,area,city_con,country_con,district,domain,domain_con,station_code," +
            "station_id,station_id_dz,station_name,station_status,online_flag,protocol,protocol_name,street,station_major,"+
            "station_setup,station_setupdate,company_code,climate,radar,station_position,station_range,station_attribute,station_sim,operation_id) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12," +
            "?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23,?24,?25,?26,?27)",nativeQuery = true)
    void insertStation(String application,int area,int city_con,int country_con,String district,int domain,int domain_con,
                       String station_code,String station_id,String station_id_dz,String station_name,int station_status,
                       int online_flag,int protocol,String protocol_name,String street,String station_major,String station_setup,
                       String station_setupdate,String company_code,int climate,int radar,String station_position,String station_range,int station_attribute,String station_sim,String operation_id);

    /*删除某一个站点*/
    @Transactional
    @Modifying
    @Query(value="delete from station where station_code=?1",nativeQuery = true)
    void deleteStation(String station_code);

    /*修改某一个站点信息*/
    @Transactional
    @Modifying
    @Query(value="update station set area=?1,application=?2,city_con=?3,country_con=?4,district=?5," +
            "domain=?6,domain_con=?7,station_code=?8,station_id=?9,station_id_dz=?10,station_name=?11 " +
            ",station_status=?12,online_flag=?13,protocol=?14,protocol_name=?15,street=?16,station_major=?17" +
            ",station_setup=?18,station_setupdate=?19,company_code=?20,climate=?21,radar=?22,station_position=?23,"+
            "station_range=?24,station_attribute=?25,station_sim=?26,operation_id=?27 where station_code=?28",nativeQuery = true)
    void updateStation(int area,String application,int city_con,int country_con,String district,int domain,int domain_con,
                       String station_code,String station_id,String station_id_dz,String station_name,int station_status,
                       int online_flag,int protocol,String protocol_name,String street,String station_major,String station_setup,
                       String station_setupdate,String company_code,int climate,int radar,String station_position,String station_range,
                       int station_attribute,String station_sim,String operation_id,String target);

    @Query(value = "select * from station s where (s.station_code like %?2% or s.station_name like %?2%) and district = ?1", nativeQuery = true)
    List<Station> findByStationCodeNameLikeAndArea(String area, String query);

    /**
     * 修改某一个站点的运维单位信息*/
    @Transactional
    @Modifying
    @Query(value = "update station set operation_id=?1 where station_code=?2",nativeQuery = true)
    void updateStationOperation(String operation_id,String station_code);

    @Query(value = "select * from station s where operation_id = ?1", nativeQuery = true)
    List<Station> findByOperationId(String operatationId);


    @Query(value = "select * from station where district = ?1 and domain = ?2", nativeQuery = true)
    List<Station> findByDistrictAndDomain(String district, int domain);

    /**
     * 筛选有运维单位的站点模糊查询*/
    @Query(value = "select * from station where district=?1 and (operation_id='' or operation_id=?2) and (station_code like %?3% or station_name like %?3%)",nativeQuery = true)
    List<Station> getOperationStationLike(String district,String operation_id,String key);

    @Query(value = "select * from station where (operation_id='' or operation_id=?1) and (station_code like %?2% or station_name like %?2%)",nativeQuery = true)
    List<Station> getOperationStationLikeAll(String operation_id,String key);


    /**
     * 分页综合查询站点
     * @param area
     * @param environment
     * @param isCountry
     * @param isCity
     * @param isArea
     * @param attribute
     * @param district
     * @param street
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select * from station s where if(?1 !='' ,s.DOMAIN = ?1,1=1) and if(?2 !='' ,s.AREA = ?2,1=1) and if(?3 !='' ,s.COUNTRY_CON = ?3,1=1) and if(?4 !='' ,s.CITY_CON = ?4, 1=1) " +
            "and if(?5 !='' ,s.DOMAIN_CON=?5,1=1) and if(?6 !='' ,s.station_attribute = ?6,1=1) and if(?7 !='' ,s.DISTRICT = ?7,1=1) and if(?8 !='' ,s.STREET = ?8,1=1) and if(?9 !='',s.operation_id = ?9,1=1) order by s.STATION_ID asc limit ?10,?11", nativeQuery = true)
    List<Station> comprehensiveQueryByPage(String area, String environment, String isCountry, String isCity, String isArea, String attribute, String district, String street, String userOperationId, Integer start, Integer end);

    /**
     * 查询符合站点的信息条数
     * @param area
     * @param environment
     * @param isCountry
     * @param isCity
     * @param isArea
     * @param attribute
     * @param district
     * @param street
     * @return
     */
    @Query(value = "select count(*) from station s where if(?1 !='' ,s.DOMAIN = ?1,1=1) and if(?2 !='' ,s.AREA = ?2,1=1) and if(?3 !='' ,s.COUNTRY_CON = ?3,1=1) and if(?4 !='' ,s.CITY_CON = ?4, 1=1) " +
            "and if(?5 !='' ,s.DOMAIN_CON=?5,1=1) and if(?6 !='' ,s.station_attribute = ?6,1=1) and if(?7 !='' ,s.DISTRICT = ?7,1=1) and if(?8 !='' ,s.STREET = ?8,1=1) and if(?9 !='',s.operation_id = ?9,1=1)", nativeQuery = true)
    int queryStationMunByComprehensiveQuery(String area, String environment, String isCountry, String isCity, String isArea, String attribute, String district, String street, String userOperationId);

    /**
     * 查询站点名称
     * @param station_id
     * @return
     */
    @Query(value = "select STATION_NAME from station where STATION_CODE = ?1", nativeQuery = true)
    String findStationNameByStationId(String station_id);
}
