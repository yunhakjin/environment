package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.M5DataDao;
import com.springboot.environment.dao.NormDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.M5DataService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.NormConstant;
import com.springboot.environment.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class M5DataServiceImp implements M5DataService {
    @Autowired
    private M5DataDao m5DataDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private NormDao normDao;

    private static final int MINUTE_5 = 12;

    @Override
    public List<M5Data> getAll() {
        return m5DataDao.findAll();
    }


    @Override
    public List<M5Data> getM5DataByData_id(String Data_id) {
        return m5DataDao.getAllByData_id(Data_id);
    }

    @Override
    public String queryM5dataByStationIdAndDatetime(String stationId, String date) {

        String startDate = date;
        String endDate = DateUtil.getDateAfter1Hour(startDate);

        List<M5Data> m5Datas = m5DataDao.queryMdataByStationIdAndTime(stationId, startDate, endDate);

        JSONArray m5dataArray = new JSONArray();
        JSONObject m5dataJSON = new JSONObject();
        JSONObject dataJSON = new JSONObject();

        //如果指定时间内有数据
        if (!StringUtil.isNullOrEmpty(m5Datas)){
            Map<Date, List<M5Data>> map = Maps.newTreeMap();
            for (M5Data m5Data : m5Datas) {
                if (map.containsKey(m5Data.getData_time())) {
                    map.get(m5Data.getData_time()).add(m5Data);
                } else {
                    List<M5Data> m5DataList = Lists.newArrayList();
                    m5DataList.add(m5Data);
                    map.put(m5Data.getData_time(), m5DataList);
                }
            }

            for (int i = 0; i < MINUTE_5; i++) {
                String time = DateUtil.getDateAfterMinutes(startDate, 5 * i);
                JSONObject object = new JSONObject();
                object.put("time", time);
                m5dataArray.add(object);
            }

            for (List<M5Data> m5DataList : map.values()) {
                String time = DateUtil.getHourAndMinute(m5DataList.get(0).getData_time());
                for (int i = 0; i < m5dataArray.size(); i++) {
                    JSONObject object = m5dataArray.getJSONObject(i);
                    if (time.equals(object.getString("time"))){
                        for (M5Data m5Data : m5DataList) {
                            if (m5Data.getNorm_code() != null && m5Data.getNorm_val() != null) {
                                object.put(m5Data.getNorm_code(), m5Data.getNorm_val());
                            }
                        }
                        break;
                    }
                }
            }
            //获取最新数据的val值
            String latestCal = null;
            List<M5Data> latestM5Data = ((TreeMap<Date, List<M5Data>>) map).lastEntry().getValue();
            System.out.println("最新的时间为:" + latestM5Data.get(0).getData_time());
            for (M5Data m5Data : latestM5Data){
                if (m5Data.getNorm_code().equals("n00100")){
                    latestCal = m5Data.getNorm_val();
                }
            }
            //一小时的固定5分钟数据是12条，从 00 到55
            dataJSON.put("count", 12);
            dataJSON.put("data", m5dataArray);
            dataJSON.put("latest_calibration_value", latestCal);
            m5dataJSON.put("siteData", dataJSON);

            System.out.println(m5dataJSON.toJSONString());
            return m5dataJSON.toJSONString();
        }

        else {
            dataJSON.put("count", 0);
            dataJSON.put("data", "");
            dataJSON.put("latest_calibration_value", "");
            m5dataJSON.put("siteData", dataJSON);

            System.out.println(m5dataJSON.toJSONString());
            return m5dataJSON.toJSONString();
        }
    }

    @Override
    public Map getM5StationsData(Map params) {
        //{"station_id":"31010702330051",  "timeRange":{ "time1":"2018-10-29",   "time2":"2018-10-30"   }}
        Map<String,String> timeRange=(Map<String, String>) params.get("timeRange");
        String station_id=params.get("station_id")+"";
        String time1=timeRange.get("time1")+"";
        String time2=timeRange.get("time2")+"";
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();


        Station station=stationDao.findStationByStationId(station_id);
        String station_name= station.getStationName();

        List<M5Data> dDatas_time1=m5DataDao.getByStationAndHour(station_id,time1);
        List<M5Data> dDatas_time2=m5DataDao.getByStationAndHour(station_id,time2);


        Map<String, Object> time1Map = new LinkedHashMap<String, Object>();//time1
        Map<String, Object> time2Map = new LinkedHashMap<String, Object>();//time2
        time1Map.put("time",time1+":00");
        time2Map.put("time",time2+":00");
        Map<String,Map> innertrackMap=new HashMap<String,Map>();
        Map<String,Map> innertrack2Map=new HashMap<String,Map>();

        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf3=new SimpleDateFormat("mm");


        if (dDatas_time1 != null) {
            for (int i = 0; i < dDatas_time1.size(); i++) {
                String trackTime = sdf3.format(dDatas_time1.get(i).getData_time());
                Map<String, String> normVal = new HashMap<String, String>();
                if (innertrackMap.containsKey(trackTime)) {
                    normVal.put(dDatas_time1.get(i).getNorm_code(), dDatas_time1.get(i).getNorm_val());
                    innertrackMap.get(trackTime).putAll(normVal);
                } else {
                    normVal.put("time", sdf2.format(dDatas_time1.get(i).getData_time()));
                    normVal.put(dDatas_time1.get(i).getNorm_code(), dDatas_time1.get(i).getNorm_val());
                    innertrackMap.put(trackTime, normVal);
                }
            }
        }
        if (dDatas_time2 != null) {
            for (int i = 0; i < dDatas_time2.size(); i++) {
                String trackTime = sdf3.format(dDatas_time2.get(i).getData_time());
                Map<String, String> normVal = new HashMap<String, String>();
                if (innertrack2Map.containsKey(trackTime)) {
                    normVal.put(dDatas_time2.get(i).getNorm_code(), dDatas_time2.get(i).getNorm_val());
                    innertrack2Map.get(trackTime).putAll(normVal);
                } else {
                    normVal.put("time", sdf2.format(dDatas_time2.get(i).getData_time()));
                    normVal.put(dDatas_time2.get(i).getNorm_code(), dDatas_time2.get(i).getNorm_val());
                    innertrack2Map.put(trackTime, normVal);
                }
            }
        }

        List<Map> innerTrackList=new ArrayList<Map>();
        List<Map> innerTrackList2=new ArrayList<Map>();
        List<Norm> normList=normDao.getAllByM5flag();
        List<Norm> normList2=normDao.getAllByM5flag();


        for(int i=0;i<12;i++){
            if(i==0){
                if(!innertrackMap.containsKey("00")){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time1+":00");
                    innertrackMap.put("00",map);
                }
            }else if(i==1){
                if(!innertrackMap.containsKey("05")){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time1+":05");
                    innertrackMap.put("05",map);
                }
            }else{
                if(!innertrackMap.containsKey(""+5*i)){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time1+":"+5*i);
                    innertrackMap.put(5*i+"",map);
                }
            }


        }
//list2
        for(int i=0;i<12;i++){
            if(i==0){
                if(!innertrack2Map.containsKey("00")){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time2+":00");
                    innertrack2Map.put("00",map);
                }
            }else if(i==1){
                if(!innertrack2Map.containsKey("05")){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time2+":05");
                    innertrack2Map.put("05",map);
                }
            }else{
                 if(!innertrack2Map.containsKey(5*i+"")){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("time",time2+":"+5*i);
                    innertrack2Map.put(""+5*i,map);
                }
            }

        }

        for(Map value:innertrackMap.values()){
            innerTrackList.add(value);
        }
        for(Map value:innertrack2Map.values()){
            innerTrackList2.add(value);
        }
        Collections.sort(innerTrackList, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String map1value =o1.get("time")+"";
                String map2value =o2.get("time")+"";
                return map1value.compareTo(map2value);
            }
        });
        Collections.sort(innerTrackList2, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String map1value =o1.get("time")+"";
                String map2value =o2.get("time")+"";
                return map1value.compareTo(map2value);
            }
        });
        int count=innerTrackList.size()+innerTrackList2.size();
        resultMap.put("count", count);
        resultMap.put("station_id",station_id);
        resultMap.put("station_name",station_name);
        time1Map.put("data",innerTrackList);
        time2Map.put("data",innerTrackList2);
        resultMap.put("time1",time1Map);
        resultMap.put("time2",time2Map);
        return resultMap;
    }

    @Override
    public Map getmanyM5databystationanddata(Map params) {
        //String query="{"query":{"stations": ["31010702335001","31010702335002"],"time":"2018-10-30"}}"
        List<Norm> normList=normDao.getAllByM5flag();
        Map query=(Map)params.get("query");
        Map<String,Map> resultMap=new HashMap<String,Map>();
        List<String> stationList=(List)query.get("stations");//获取所有的stationIDList
        String date=(String)query.get("time");//2018-11-17 04
        List<Map> dataList=new ArrayList<Map>();
        SimpleDateFormat sdf=new SimpleDateFormat("mm");
        SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf3=new SimpleDateFormat("HH");
        int count=0;
        int error_count=0;
        for(String station:stationList){
            String station_id=station;
            Station station1=stationDao.findStationByStationId(station_id);
            String station_name=station1.getStationName();
            List<M5Data> innerDataList=m5DataDao.getByStationAndHour(station_id,date);//获得5分钟数据中的整点信息
            if(innerDataList == null || innerDataList.isEmpty()) error_count++;
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            if (innerDataList != null) {
                for (M5Data m5Data : innerDataList) {
                    String dateKey = sdf.format(m5Data.getData_time());
                    String time = sdf2.format(m5Data.getData_time());
                    if (innerMap.containsKey(dateKey)) {
                        innerMap.get(dateKey).put(m5Data.getNorm_code(), m5Data.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_id", station_id);
                        normVal.put("station_name", station_name);
                        normVal.put("station_Sim", station1.getStationSim());
                        normVal.put("time", time);
                        normVal.put(m5Data.getNorm_code(), m5Data.getNorm_val());
                        innerMap.put(dateKey, normVal);
                    }
                }
            }
            for(int i=0;i<12;i++){

                if(i==0){
                    if(!innerMap.containsKey("00")){
                        Map<String,String> map=new HashMap<String, String>();
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",date.substring(date.length()-2)+":00");
                        innerMap.put("00",map);
                    }
                }else if(i==1){
                    if(!innerMap.containsKey("05")){
                        Map<String,String> map=new HashMap<String, String>();
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",date.substring(date.length()-2)+":05");
                        innerMap.put("05",map);
                    }
                }else{
                    if(!innerMap.containsKey(5*i+"")){
                        Map<String,String> map=new HashMap<String, String>();
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",date.substring(date.length()-2)+":"+5*i);
                        System.out.println(date.substring(date.length()-2)+":"+5*i);
                        innerMap.put(""+5*i,map);
                    }
                }
                }

            innerMap=innerMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:innerMap.values()){
                innerList.add(value);
                count++;
            }
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("time",date+":00");
            tmp.put("data",innerList);
            dataList.add(tmp);
        }
        if(error_count==stationList.size()) return null;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("datas",dataList);
        resultMap.put("stationData",map);
        return resultMap;
    }

}

