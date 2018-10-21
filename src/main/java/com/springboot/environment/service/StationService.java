package com.springboot.environment.service;

import com.springboot.environment.bean.Station;

import java.util.List;

public interface StationService {

    List<Station> findALl();

    List<Station> queryStationsByCountryCon(int isCountryCon);

    List<Station> queryStationsByStatus(int stationStatus);

    List<Station> queryStationsByOnlineFlag(int onlineFlag);

    List<Station> queryStationsByNameLike(String stationName);

    List<Station> queryStationsByDistrict(String district);

    List<Station> queryStationsByStreet(String street);

    List<Station> queryStationsByCityCon(int isCityCon);

    List<Station> queryStationsByDomainCon(int isDomainCon);

    List<Station> queryStationsByArea(int area);

    List<Station> queryStationsByDomain(int domain);


}
