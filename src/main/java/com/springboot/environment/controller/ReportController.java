package com.springboot.environment.controller;

import com.springboot.environment.bean.Station;
import com.springboot.environment.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/report")
@Api("报表api")
public class ReportController {
    @Autowired
    private NormService normService;
    @Autowired
    private StationService stationService;
    @Autowired
    private DDataService dDataService;
    @Autowired
    private HDataService hDataService;
    @Autowired
    private MDataService mDataService;
    @Autowired
    private M5DataService m5DataService;

    @ApiOperation(value = "报表一",notes = "区域监测点位基础信息表")
    @RequestMapping(value = "/report1",method = RequestMethod.GET)
    public Map report1(){
        Random random=new Random();
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Station> stationList=stationService.findALl();
        List<Map> resultList=new ArrayList<Map>();
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            String station_id=station.getStationId();
            String station_name=station.getStationName();
            String pos=station.getPosition();
            String station_lon="";
            String station_lat="";
            if(!pos.isEmpty()){
                station_lon=pos.split(",")[1];
                station_lat=pos.split(",")[0];
            }
            String reference=station.getStreet();
            String population=String.valueOf(random.nextInt(2000));
            String area_id=String.valueOf(station.getArea());
            String remark="";
            map.put("station_id",station_id);
            map.put("station_name",station_name);
            map.put("station_lon",station_lon);
            map.put("station_lat",station_lat);
            map.put("reference",reference);
            map.put("population",population);
            map.put("area_id",area_id);
            map.put("remark",remark);
            resultList.add(map);
        }
        resultMap.put("siteData",resultList);
        return resultMap;
    }
    @ApiOperation(value = "报表二",notes = "区域监测记录表")
    @RequestMapping(value = "/report2",method = RequestMethod.GET)
    public Map report2(){
        Random random=new Random();
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Station> stationList=stationService.findALl();
        List<Map> resultList=new ArrayList<Map>();
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            String station_id=station.getStationId();
            String station_name=station.getStationName();
            String pos=station.getPosition().replace("(","").replace(")","").replace(" ","");
            String station_lon=pos.split(",")[0];
            String station_lat=pos.split(",")[1];
            String reference=station.getStreet();
            String population=String.valueOf(random.nextInt(2000));
            String road_name=station.getStreet();
            String road_end=station.getStreet();
            String road_length=String.valueOf(random.nextInt(5000));
            String road_width=String.valueOf(random.nextInt(100));
            String road_level=String.valueOf(random.nextInt(5));
            String remark="";
            map.put("station_id",station_id);
            map.put("station_name",station_name);
            map.put("station_lon",station_lon);
            map.put("station_lat",station_lat);
            map.put("reference",reference);
            map.put("road_name",road_name);
            map.put("road_end",road_end);
            map.put("road_length",road_length);
            map.put("road_width",road_width);
            map.put("road_level",road_level);
            map.put("population",population);
            map.put("remark",remark);
            resultList.add(map);
        }
        resultMap.put("areaData",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表三",notes = "功能区监测点位基础信息表")
    @RequestMapping(value = "/report3",method = RequestMethod.GET)
    public Map report3(){
        Random random=new Random();
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Station> stationList=stationService.findALl();
        List<Map> resultList=new ArrayList<Map>();
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            String station_id=station.getStationId();
            String station_name=station.getStationName();
            String pos=station.getPosition().replace("(","").replace(")","").replace(" ","");
            String station_lon=pos.split(",")[0];
            String station_lat=pos.split(",")[1];
            String station_height=String.valueOf(random.nextInt(4));
            String reference=station.getStreet();
            String functional_area_id=String.valueOf(station.getDomain());
            String remark="";
            map.put("station_id",station_id);
            map.put("station_name",station_name);
            map.put("station_lon",station_lon);
            map.put("station_lat",station_lat);
            map.put("station_height",station_height);
            map.put("reference",reference);
            map.put("functional_area_id",functional_area_id);
            map.put("remark",remark);
            resultList.add(map);
        }
        resultMap.put("functionalAreaData",resultList);
        return resultMap;
    }




}
