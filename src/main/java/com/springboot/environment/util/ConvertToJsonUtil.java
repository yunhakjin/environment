package com.springboot.environment.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Station;

import java.util.ArrayList;
import java.util.List;

public class ConvertToJsonUtil {

    public static String stationListConvertToJson(List<Station> stations){

        if (stations == null || stations.size() == 0 || stations.isEmpty()){
            JSONObject emptyStation = new JSONObject();
            emptyStation.put("message", "查询结果为空");
            return emptyStation.toJSONString();
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
            stationJson.put("stationPosition",station.getPosition());
            stationJson.put("street",station.getStreet());
            stationJson.put("district",station.getDistrict());
            List<String> range=new ArrayList<String>();
            if(station.getRange()!=""){
                for(int i=0;i<station.getRange().split(";").length;i++){
                    range.add(station.getRange().split(";")[i]);

                }
            }
            stationJson.put("stationRange",range);
            stationJson.put("countryCon",station.getCountryCon());
            stationJson.put("cityCon",station.getCityCon());
            stationJson.put("domainCon",station.getDomainCon());
            stationJson.put("area",station.getArea());
            stationJson.put("domain",station.getDomain());
            stationJson.put("stationAttribute",station.getStation_attribute());
            stationJson.put("stationMajor",station.getStation_major());
            stationJson.put("stationSetup",station.getStation_setup());
            stationJson.put("stationSetupDate",station.getStation_setupdate().toString());
            stationJson.put("companyCode",station.getCompany_code());
            stationJson.put("climate",station.getClimate());
            stationJson.put("radar",station.getRadar());

            stationsArray.add(stationJson);
        }

        System.out.println("查询到的站点信息为");

        return stationsArray.toJSONString();
    }
}
