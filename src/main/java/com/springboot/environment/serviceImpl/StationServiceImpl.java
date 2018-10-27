package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.StationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao stationDao;


    @Override
    public List<Station> findALl() {
        return stationDao.findAll();
    }

    @Override
    public List<Station> queryStationsByCountryCon(int isCountryCon) {

        if (isCountryCon != StationConstant.STATION_IS_COUNTRY_CON && isCountryCon != StationConstant.STATION_ISNOT_COUNTRY_CON){
           throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByCountryCon(isCountryCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByStatus(int stationStatus) {

        if (stationStatus != StationConstant.STATION_RUN && stationStatus != StationConstant.STATION_DISABLE){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByStationStatus(stationStatus);
        return stations;
    }

    @Override
    public List<Station> queryStationsByOnlineFlag(int onlineFlag) {

        if (onlineFlag != StationConstant.STATION_ONLINE && onlineFlag != StationConstant.STATION_OFFLINE){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByOnlineFlag(onlineFlag);
        return stations;
    }

    @Override
    public List<Station> queryStationsByNameLike(String stationName) {
        List<Station> stations = stationDao.findByStationNameLike(stationName);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDistrict(String district) {
        List<Station> stations = stationDao.findByDistrict(district);
        return stations;
    }

    @Override
    public List<Station> queryStationsByStreet(String street) {
        List<Station> stations = stationDao.findByStreetLike(street);
        return stations;
    }

    @Override
    public List<Station> queryStationsByCityCon(int isCityCon) {

        if (isCityCon != StationConstant.STATION_IS_CITY_CON && isCityCon != StationConstant.STATION_ISNOT_CITY_CON){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByCityCon(isCityCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDomainCon(int isDomainCon) {
        if (isDomainCon != StationConstant.STATION_IS_DOMAIN_CON && isDomainCon != StationConstant.STATION_ISNOT_DOMAIN_CON){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByDomainCon(isDomainCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByArea(int area) {
        List<Station> stations = stationDao.findByArea(area);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDomain(int domain) {
        List<Station> stations = stationDao.findByDomain(domain);
        return stations;
    }

    @Transactional
    @Override
    public String addStation(String stationId, String stationCode, String stationName, String stationStatus, String application, String onlineFlag, String stationIdDZ, String protocol, String protocolName, String position, String street, String district, String range, String countryCon, String cityCon, String domainCon, String area, String domain) {
        Station station = new Station();
        station.setStationId(stationId);
        station.setStationCode(stationCode);
        station.setStationName(stationName);
        station.setStationStatus(Integer.parseInt(stationStatus));
        station.setApplication(application);
        station.setOnlineFlag(Integer.parseInt(onlineFlag));
        station.setStationIdDZ(stationIdDZ);
        station.setProtocol(Integer.parseInt(protocol));
        station.setProtocolName(protocolName);
        station.setPosition(position);
        station.setStreet(street);
        station.setDistrict(district);
        station.setRange(range);
        station.setCountryCon(Integer.parseInt(countryCon));
        station.setCityCon(Integer.parseInt(cityCon));
        station.setDomainCon(Integer.parseInt(domainCon));
        station.setArea(Integer.parseInt(area));
        station.setDomainCon(Integer.parseInt(domain));
        System.out.println(station.toString());
        Station result = stationDao.save(station);
        if (result != null){
            return "新增成功";
        }

        return "新增失败";
    }

    @Override
    public String deleteStationByStationId(String stationId) {
        Station station = stationDao.findByStationId(stationId);
        if (station == null){
            return "该站点信息不存在";
        }

        stationDao.deleteByStationId(stationId);
        return "删除成功";
    }

    @Override
    public Station queryStatiionByCode(String stationCode) {
        return stationDao.findByStationCode(stationCode);
    }
}
