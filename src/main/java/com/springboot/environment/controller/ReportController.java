package com.springboot.environment.controller;

import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Station;
import com.springboot.environment.service.*;
import com.springboot.environment.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
            String station_code=station.getStationCode();
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
            map.put("station_code",station_code);
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
            String station_code=station.getStationCode();
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
            map.put("station_code",station_code);
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
            String station_code=station.getStationCode();
            String station_name=station.getStationName();
            String pos=station.getPosition().replace("(","").replace(")","").replace(" ","");
            String station_lon=pos.split(",")[0];
            String station_lat=pos.split(",")[1];
            String station_height=String.valueOf(random.nextInt(4));
            String reference=station.getStreet();
            String functional_area_id=String.valueOf(station.getDomain());
            String remark="";
            map.put("station_code",station_code);
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

    @ApiOperation(value = "报表四",notes = "区域声环境监测记录表")
    @RequestMapping(value = "/report4",method = RequestMethod.POST)
    public Map report4(@RequestBody Map<String,String> params){
        //{"area":"1","time":"2018-10-28"}
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        int area=Integer.valueOf(params.get("area"));
        String time=params.get("time");
        int error_count=0;
        int count=0;
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_day=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        List<Station> stationList=stationService.queryStationsByArea(area);
        List<Norm> normList=normService.getAllByHflag();
        for(Station station:stationList){
            String station_name=station.getStationName();
            String station_code=station.getStationCode();
            String noise_code=String.valueOf(station.getProtocol());
            List<HData> hDataList=hDataService.getByStationAndDate(station_code,time);
            if(hDataList.isEmpty()) error_count++;
            Map<String,Map> timeMap=new HashMap<String,Map>();
            if(!StringUtil.isNullOrEmpty(hDataList)) {
                for (HData hData : hDataList) {
                    String dateKey = hData.getData_time().toString();
                    if (timeMap.containsKey(dateKey)) {
                        timeMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_code", station_code);
                        normVal.put("station_name", station_name);
                        normVal.put("month", sdf_month.format(hData.getData_time()));
                        normVal.put("date", sdf_day.format(hData.getData_time()));
                        normVal.put("hour", sdf_hour.format(hData.getData_time()));
                        normVal.put("minute", sdf_minute.format(hData.getData_time()));
                        normVal.put("noise_code", noise_code);
                        normVal.put("remark", "");
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        timeMap.put(dateKey, normVal);
                    }
                }
            }
            timeMap=timeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:timeMap.values()){
                resultList.add(value);
                count++;
            }
        }
        if(count==0) return null;
        resultMap.put("areaMonitorData",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表五",notes = "道路交通声环境监测记录表")
    @RequestMapping(value = "/report5",method = RequestMethod.POST)
    public Map report5(@RequestBody Map<String,String> params){
        //{"road":"中山街197号附近","time":"2018-10-28"}
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        String road=params.get("road");
        String time=params.get("time");
        int count=0;
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_day=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        List<Station> stationList=stationService.queryStationsByStreet(road);
        List<Norm> normList=normService.getAllByHflag();
        for(Station station:stationList){
            String station_name=station.getStationName();
            String station_code=station.getStationCode();
            String noise_code=String.valueOf(station.getProtocol());
            List<HData> hDataList=hDataService.getByStationAndDate(station_code,time);
            Map<String,Map> timeMap=new HashMap<String,Map>();
            if (!StringUtil.isNullOrEmpty(hDataList)) {
                for (HData hData : hDataList) {
                    String dateKey = hData.getData_time().toString();
                    if (timeMap.containsKey(dateKey)) {
                        timeMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_code", station_code);
                        normVal.put("station_name", station_name);
                        normVal.put("month", sdf_month.format(hData.getData_time()));
                        normVal.put("date", sdf_day.format(hData.getData_time()));
                        normVal.put("hour", sdf_hour.format(hData.getData_time()));
                        normVal.put("minute", sdf_minute.format(hData.getData_time()));
                        normVal.put("remark", "");
                        normVal.put("large_car", String.valueOf((int) (Math.random() * 50 + 51)));
                        normVal.put("small_car", String.valueOf((int) (Math.random() * 50)));
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        timeMap.put(dateKey, normVal);
                    }
                }
            }
            timeMap=timeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:timeMap.values()){
                resultList.add(value);
                count++;
            }
        }
        if(count==0) return null;
        resultMap.put("roadMonitorData",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表六",notes = "功能区声环境24小时监测记录表")
    @RequestMapping(value = "/report6",method = RequestMethod.POST)
    public Map report6(@RequestBody Map<String,String> params){
        //{"station_id":"31010702335001","time":"2018-10-27"}
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        String station_code=params.get("station_code");
        String time=params.get("time");
        int count=0;
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_day=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        List<HData> hDataList=hDataService.getByStationAndDate(station_code,time);
        if(hDataList == null || hDataList.isEmpty()){
            return null;
        }
        List<Norm> normList=normService.getAllByHflag();
        Map<String,Map> innerMap=new HashMap<String,Map>();
        String month="";
        String date="";
        for(HData hData:hDataList){
            String dateKey=sdf_hour.format(hData.getData_time());
            if(innerMap.containsKey(dateKey)){
                innerMap.get(dateKey).put(hData.getNorm_code(),hData.getNorm_val());
            }
            else{
                Map<String,String> normVal=new HashMap<String,String>();
                month=sdf_month.format(hData.getData_time());
                normVal.put("month",month);
                date=sdf_day.format(hData.getData_time());
                normVal.put("date",date);
                normVal.put("hour",sdf_hour.format(hData.getData_time()));
                normVal.put(hData.getNorm_code(),hData.getNorm_val());
                normVal.put("remark","");
                innerMap.put(dateKey,normVal);
            }
        }
//        for(int i=0;i<10;i++){
//            if(!innerMap.containsKey("0"+i)){
//                Map<String,String> map=new HashMap<String,String>();
//                for(Norm norm:normList){
//                    map.put(norm.getNorm_code(),"");
//                }
//                map.put("month",month);
//                map.put("date",date);
//                map.put("hour","0"+i);
//                map.put("remark","");
//                innerMap.put("0"+i,map);
//            }
//        }
//        for(int i=10;i<24;i++){
//            if(!innerMap.containsKey(i)){
//                Map<String,String> map=new HashMap<String,String>();
//                for(Norm norm:normList){
//                    map.put(norm.getNorm_code(),"");
//                }
//                map.put("month",month);
//                map.put("date",date);
//                map.put("hour",String.valueOf(i));
//                map.put("remark","");
//                innerMap.put(String.valueOf(i),map);
//            }
//        }
        innerMap=innerMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
        for(Map value:innerMap.values()){
            resultList.add(value);
            count++;
        }
        resultMap.put("functionalAreaMonitorData",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表七",notes = "区域声环境监测结果统计表")
    @RequestMapping(value = "/report7",method = RequestMethod.POST)
    public Map report7(@RequestBody Map<String,String> params){
        //{"start":"2018-10-27","end":"2018-10-30"}
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        String start=params.get("start");
        String end=params.get("end");
        List<Station> stationList=stationService.findALl();
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_date=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        int count=0;
        for(Station station:stationList){
            String station_name=station.getStationName();
            String station_code=station.getStationCode();
            String noise_code=String.valueOf(station.getProtocol());
            String area=String.valueOf(station.getArea());
            String remark="";
            List<HData> hDataList=hDataService.getByStationAndTime(station_code,start,end);
            Map<String,Map> timeMap=new HashMap<String,Map>();
            if (!StringUtil.isNullOrEmpty(hDataList)) {
                for (HData hData : hDataList) {
                    String dateKey = hData.getData_time().toString();
                    if (timeMap.containsKey(dateKey)) {
                        timeMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_code", station_code);
                        normVal.put("station_name", station_name);
                        normVal.put("month", sdf_month.format(hData.getData_time()));
                        normVal.put("date", sdf_date.format(hData.getData_time()));
                        normVal.put("hour", sdf_hour.format(hData.getData_time()));
                        normVal.put("minute", sdf_minute.format(hData.getData_time()));
                        normVal.put("noise_code", noise_code);
                        normVal.put("area_id", area);
                        normVal.put("remark", remark);
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        timeMap.put(dateKey, normVal);
                    }
                }
            }
            timeMap=timeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:timeMap.values()){
                resultList.add(value);
                count++;
            }
        }
        if(count==0) return null;
        resultMap.put("areaDataSummary",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表八",notes = "道路交通声环境监测结果统计表")
    @RequestMapping(value = "/report8",method = RequestMethod.POST)
    public Map report8(@RequestBody Map<String,String> params){
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        String start=params.get("start");
        String end=params.get("end");
        List<Station> stationList=stationService.findALl();
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_date=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        int count=0;
        for(Station station:stationList){
            String station_name=station.getStationName();
            String station_code=station.getStationCode();
            String noise_code=String.valueOf(station.getProtocol());
            String area=String.valueOf(station.getArea());
            String remark="";
            List<HData> hDataList=hDataService.getByStationAndTime(station_code,start,end);
            Map<String,Map> timeMap=new HashMap<String,Map>();
            if (!StringUtil.isNullOrEmpty(hDataList)) {
                for (HData hData : hDataList) {
                    String dateKey = hData.getData_time().toString();
                    if (timeMap.containsKey(dateKey)) {
                        timeMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_code", station_code);
                        normVal.put("station_name", station_name);
                        normVal.put("month", sdf_month.format(hData.getData_time()));
                        normVal.put("date", sdf_date.format(hData.getData_time()));
                        normVal.put("hour", sdf_hour.format(hData.getData_time()));
                        normVal.put("minute", sdf_minute.format(hData.getData_time()));
                        normVal.put("large_car", String.valueOf((int) (Math.random() * 50 + 51)));
                        normVal.put("small_car", String.valueOf((int) (Math.random() * 50)));
                        normVal.put("remark", remark);
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        timeMap.put(dateKey, normVal);
                    }
                }
            }
            timeMap=timeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:timeMap.values()){
                resultList.add(value);
                count++;
            }
        }
        if(count==0) return null;
        resultMap.put("roadDataSummary",resultList);
        return resultMap;
    }

    @ApiOperation(value = "报表九",notes = "功能区声环境监测结果统计表")
    @RequestMapping(value = "/report9",method = RequestMethod.POST)
    public Map report9(@RequestBody Map<String,String> params){
        Map<String,List> resultMap=new HashMap<String,List>();
        List<Map> resultList=new ArrayList<Map>();
        String start=params.get("start");
        String end=params.get("end");
        List<Station> stationList=stationService.findALl();
        SimpleDateFormat sdf_month=new SimpleDateFormat("MM");
        SimpleDateFormat sdf_date=new SimpleDateFormat("dd");
        SimpleDateFormat sdf_hour=new SimpleDateFormat("HH");
        SimpleDateFormat sdf_minute=new SimpleDateFormat("mm");
        int count=0;
        for(Station station:stationList){
            String station_name=station.getStationName();
            String station_code=station.getStationCode();
            String noise_code=String.valueOf(station.getProtocol());
            String area=String.valueOf(station.getArea());
            String remark="";
            List<HData> hDataList=hDataService.getByStationAndTime(station_code,start,end);
            Map<String,Map> timeMap=new HashMap<String,Map>();
            if (!StringUtil.isNullOrEmpty(hDataList)) {
                for (HData hData : hDataList) {
                    String dateKey = hData.getData_time().toString();
                    if (timeMap.containsKey(dateKey)) {
                        timeMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_code", station_code);
                        normVal.put("station_name", station_name);
                        normVal.put("month", sdf_month.format(hData.getData_time()));
                        normVal.put("date", sdf_date.format(hData.getData_time()));
                        normVal.put("hour", sdf_hour.format(hData.getData_time()));
                        normVal.put("area_id", area);
                        normVal.put("remark", remark);
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        timeMap.put(dateKey, normVal);
                    }
                }
            }
            timeMap=timeMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:timeMap.values()){
                resultList.add(value);
                count++;
            }
        }
        if(count==0) return null;
        resultMap.put("functionalAreaDataSummary",resultList);
        return resultMap;
    }


}
