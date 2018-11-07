package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.HDataDao;
import com.springboot.environment.dao.NormDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.HDataService;
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

@Service
public class HDataServiceImp implements HDataService {
    @Autowired
    private HDataDao hDataDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private NormDao normDao;

    @Override
    public List<HData> getAll() {
        return hDataDao.findAll();
    }

    @Override
    public Page<HData> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return hDataDao.findAll(pageable);
    }

    @Override
    public List<HData> getHDataByData_id(String Data_id) {
        return hDataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<HData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return hDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public String queryHdataByStationIdAndDatetime(String stationId, String date) {

        try {

            String dayStartTime = DateUtil.getThisDayStartTime(date);
            String dayEndTIme = DateUtil.getThisDayEndTime(date);

            List<HData> hDatas = hDataDao.queryHdataByStationIdAndTime(stationId, dayStartTime, dayEndTIme);

            JSONArray hdataArray = new JSONArray();
            JSONObject hdataJSON = new JSONObject();
            JSONObject dataJSON = new JSONObject();

            //如果指定时间内有数据
            if (!StringUtil.isNullOrEmpty(hDatas)){
                Map<Date, List<HData>> map = Maps.newTreeMap();
                for (HData hData : hDatas) {
                    if (map.containsKey(hData.getData_time())) {
                        map.get(hData.getData_time()).add(hData);
                    } else {
                        List<HData> hDataList = Lists.newArrayList();
                        hDataList.add(hData);
                        map.put(hData.getData_time(), hDataList);
                    }
                }

                System.out.println(map.toString());

                for (List<HData> hDataList : map.values()) {
                    JSONObject object = new JSONObject();
                    object.put("time", DateUtil.getHourAndMinute(hDataList.get(0).getData_time()));
                    for (HData hData : hDataList) {
                        if (hData.getNorm_code() != null && hData.getNorm_val() != null) {
                            object.put(hData.getNorm_code(), hData.getNorm_val());
                        }
                    }
                    hdataArray.add(object);
                }

                //获取最新数据的val值
                String latestCal = null;
                List<HData> latestHData = ((TreeMap<Date, List<HData>>) map).lastEntry().getValue();
                System.out.println("最新的时间为:" + latestHData.get(0).getData_time());
                for (HData hData : latestHData){
                    if (hData.getNorm_code().equals("n00100")){
                        latestCal = hData.getNorm_val();
                    }
                }
                dataJSON.put("count", map.size());
                dataJSON.put("data", hdataArray);
                dataJSON.put("latest_calibration_value", latestCal);
                hdataJSON.put("siteData", dataJSON);

                System.out.println(hdataJSON.toJSONString());
                return hdataJSON.toJSONString();
            }

            else {
                dataJSON.put("count", 0);
                dataJSON.put("data", "");
                dataJSON.put("latest_calibration_value", "");
                hdataJSON.put("siteData", dataJSON);

                System.out.println(hdataJSON.toJSONString());
                return hdataJSON.toJSONString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HData> getByStationAndDate(String station_id, String date) {
        return hDataDao.getByStationAndDate(station_id,date);
    }
    /*
    * 获取单站点的多因子数据---一天-24小时
    * */
    @Override
    public Map getStationsDataByDays(Map<String, Object> params) {
        //String stationRequest = "{station_id:\"31010702330053\", type:2,  timeRange:{ time1:\"2018-07-20\",   time2:\"2018-07-21\"   }}";

        Map<String,String> timeRange=(Map<String, String>) params.get("timeRange");
        String station_id=params.get("station_id")+"";
        String time1=timeRange.get("time1")+"";
        String time2=timeRange.get("time2")+"";
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();


        Station station=stationDao.findStationByStationId(station_id);
        String station_name= station.getStationName();

        List<HData> dDatas_time1=hDataDao.getByStationAndDate(station_id,time1);
        List<HData> dDatas_time2=hDataDao.getByStationAndDate(station_id,time2);


        Map<String, Object> time1Map = new LinkedHashMap<String, Object>();//time1
        Map<String, Object> time2Map = new LinkedHashMap<String, Object>();//time2
        Map<String, List> data1Map = new LinkedHashMap<String, List>();//data1
        Map<String, List> data2Map = new LinkedHashMap<String, List>();//data2
        time1Map.put("time",time1);
        time2Map.put("time",time2);
        Map<String,Map> innertrackMap=new HashMap<String,Map>();
        Map<String,Map> innertrack2Map=new HashMap<String,Map>();

        SimpleDateFormat sdf=new SimpleDateFormat("HH");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf3=new SimpleDateFormat("MM");


        for(int i=0;i<dDatas_time1.size();i++){
            //String trackTime=dDatas_time1.get(i).getData_time().toString();
            String trackTime=sdf.format(dDatas_time1.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrackMap.containsKey(trackTime)){
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",dDatas_time1.get(i).getData_time().toString());
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.put(trackTime,normVal);
            }
        }
        for(int i=0;i<dDatas_time2.size();i++){
            //String trackTime=dDatas_time2.get(i).getData_time().toString();
            String trackTime=sdf.format(dDatas_time2.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrack2Map.containsKey(trackTime)){
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",dDatas_time2.get(i).getData_time().toString());
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.put(trackTime,normVal);
            }
        }

        List<Map> innerTrackList=new ArrayList<Map>();
        List<Map> innerTrackList2=new ArrayList<Map>();
        List<Norm> normList=normDao.getAllByHflag();
        List<Norm> normList2=normDao.getAllByHflag();
        for(int i=0;i<10;i++){
            if(!innertrackMap.containsKey("0"+i)){
                Map<String,String> map=new HashMap<String, String>();
                for(Norm norm:normList){
                    map.put(norm.getNorm_code(),"");
                }
                map.put("time",time1+" "+"0"+i+":00:00");
                innertrackMap.put("0"+i,map);
            }
        }
        for(int i=10;i<24;i++){
            if(!innertrackMap.containsKey(i+"")){
                Map<String,String> map=new HashMap<String, String>();
                for(Norm norm:normList){
                    map.put(norm.getNorm_code(),"");
                }
                map.put("time",time1+" "+i+":00:00");
                innertrackMap.put(String.valueOf(i),map);
            }
        }

        for(int i=0;i<10;i++){
            if(!innertrack2Map.containsKey("0"+i)){
                Map<String,String> map=new HashMap<String, String>();
                for(Norm norm:normList2){
                    map.put(norm.getNorm_code(),"");
                }
                map.put("time",time2+" "+"0"+i+":00:00");
                innertrack2Map.put("0"+i,map);
            }
        }
        for(int i=10;i<24;i++){
            if(!innertrack2Map.containsKey(i+"")){
                Map<String,String> map=new HashMap<String, String>();
                for(Norm norm:normList2){
                    map.put(norm.getNorm_code(),"");
                }
                map.put("time",time2+" "+i+":00:00");
                innertrack2Map.put(String.valueOf(i),map);
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
        System.out.println(innerTrackList.size());
        System.out.println(innerTrackList2.size());
        resultMap.put("count", count);
        resultMap.put("station_id",station_id);
        resultMap.put("station_name",station_name);
        time1Map.put("data",innerTrackList);
        time2Map.put("data",innerTrackList2);
        resultMap.put("time1",time1Map);
        resultMap.put("time2",time2Map);

        return resultMap;
    }
}
