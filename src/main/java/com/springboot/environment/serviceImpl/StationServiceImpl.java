package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.StationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao stationDao;



    @Override
    public String queryStationsByCountryCon(int isCountryCon) {

        if (isCountryCon != StationConstant.STATION_IS_COUNTRY_CON && isCountryCon != StationConstant.STATION_ISNOT_COUNTRY_CON){
           throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByCountryCon(isCountryCon);
        if (stations.isEmpty()){
            JSONObject message = new JSONObject();
            message.put("message", "查询信息为空");
            return message.toJSONString();
        }

        JSONArray stationsArray = new JSONArray();
        for(Station station : stations){
            JSONObject stationJson = new JSONObject();
            stationJson.put("id", station.getId());
            stationJson.put("stationId",station.getStationId());
            stationJson.put("stationCode",station.getStationCode());
            stationJson.put("stationName",station.getStationName());
            stationJson.put("stationStatus",station.getStationStatus());
            stationJson.put("application",station.getApplication());
            stationJson.put("onlineFlag",station.getOnlineFlag());
            stationJson.put("stationIdDZ",station.getStationIdDZ());
            stationJson.put("protocol",station.getProtocol());
            stationJson.put("protocolName",station.getProtocolName());
            stationJson.put("position",station.getPosition());
            stationJson.put("street",station.getStreet());
            stationJson.put("district",station.getDistrict());
            stationJson.put("range",station.getRange());
            stationJson.put("countryCon",station.getCountryCon());
            stationJson.put("cityCon",station.getCityCon());
            stationJson.put("domainCon",station.getDomainCon());
            stationJson.put("area",station.getArea());
            stationJson.put("domain",station.getDomain());

            stationsArray.add(stationJson);
        }

        return stationsArray.toJSONString();
    }
}
