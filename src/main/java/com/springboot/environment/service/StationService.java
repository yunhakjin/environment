package com.springboot.environment.service;

import com.springboot.environment.bean.Station;
import com.springboot.environment.request.ComprehensiveQueryRequest;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface StationService {

    List<Station> findALl();

    List<Station> queryStationsByCountryCon(int isCountryCon);

    List<Station> queryStationsByStatus(int stationStatus);

    Station queryStatiionByCode(String stationCode);

    List<Station> queryStationsByOnlineFlag(int onlineFlag);

    List<Station> queryStationsByNameLike(String stationName);

    List<Station> queryStationsByCodeLike(String stationCode);

    List<Station> queryStationsByDistrict(String district);

    List<Station> queryStationsByStreet(String street);

    List<Station> queryStationsByCityCon(int isCityCon);

    List<Station> queryStationsByDomainCon(int isDomainCon);

    List<Station> queryStationsByArea(int area);

    List<Station> queryStationsByDomain(int domain);

    String addStation(String stationId, String stationCode, String stationName, String stationStatus, String application,
                      String onlineFlag, String stationIdDZ, String protocol, String protocolName, String position,
                      String street, String district, String range, String countryCon, String cityCon, String domainCon,
                      String area, String domain);

    String deleteStationByStationId(String stationId);

    String queryStationsByKey(String key);

    String querymDataByStationArea(Map<String, Object> params, HttpSession session) throws ParseException;

    String queryhDataByStationArea(Map<String, Object> params, HttpSession session);

    String querydDataByStationArea(Map<String, Object> params, HttpSession session);

    /**
     * 综合查询站点信息
     * @param request
     * @return
     */
    List<Station> comprehensiveQueryStations(ComprehensiveQueryRequest request);


    Map getDomainFromStation();

    Map getStationsByAreasAndFuncCodes(Map<String, Object> params, String operation_id);

    List<String> getAllStreet();

    void insertStation(Station station,String setupdate);

    void deleteStation(String station_id);

    void updateStation(Station station,String setupdate,String target);

    Station getByStationId(String station_id);

    Map GEOJson(Map params, String operation_id);

    List<Station> queryStationsByCodeLikeAndArea(String area, String query);

    List<Station> queryStationsByNameLikeAndArea(String area, String query);

    void updateStationOperation(String operation_id,String station_code);

    List<Station> findByOperationId(String operatationId);

    List<Station> queryStationsByDistrictAndDomain(String district, int domain );

    List<Station> getOperationStationLike(String district,String operation_id,String key);

    List<Station> getOperationStationLikeAll(String operation_id,String key);

    String findStationNameByStationId(String station_id);
}
