package com.springboot.environment.service;

import com.springboot.environment.bean.Station;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;

import java.util.List;

public interface StationService {

    List<Station> findALl();

    List<Station> queryStationsByCountryCon(int isCountryCon);

    List<Station> queryStationsByStatus(int stationStatus);

    Station queryStatiionByCode(String stationCode);

    List<Station> queryStationsByOnlineFlag(int onlineFlag);

    List<Station> queryStationsByNameLike(String stationName);

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

    String querymDataByStationArea(QuerymDataByStationsAreaReq req);


}
