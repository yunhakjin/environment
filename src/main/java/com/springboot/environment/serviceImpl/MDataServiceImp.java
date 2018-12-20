package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.MData;
import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.dao.NormDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.MDataService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.NormConstant;
import com.springboot.environment.util.StringUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MDataServiceImp implements MDataService {
    @Autowired
    private MDataDao mDataDao;

    @Autowired
    private NormDao normDao;

    @Autowired
    private StationDao stationDao;

    @Override
    public List<MData> getAll() {
        return mDataDao.findAll();
    }

    @Override
    public Page<MData> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.findAll(pageable);
    }

    @Override
    public List<MData> getMDataByData_id(String Data_id) {
        return mDataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<MData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public String queryMdataByStationIdAndDatetime(String stationId, String date) {

        try {
            String startDate = DateUtil.getDateBefore1hour(date);
            String endDate = date;

            List<MData> mDatas = mDataDao.queryMdataByStationIdAndTime(stationId, startDate, endDate);

            //设置比较的起始时间
            JSONArray mdataArray = new JSONArray();
            JSONObject mdataJSON = new JSONObject();
            JSONObject dataJSON = new JSONObject();

            //如果查询后有数据
            if (!StringUtil.isNullOrEmpty(mDatas)) {
                Map<Date, List<MData>> map = Maps.newTreeMap();
                for (MData mData : mDatas) {
                    if (map.containsKey(mData.getData_time())) {
                        map.get(mData.getData_time()).add(mData);
                    } else {
                        List<MData> mDataList = Lists.newArrayList();
                        mDataList.add(mData);
                        map.put(mData.getData_time(), mDataList);
                    }
                }

                for (List<MData> mDataList : map.values()) {
                    JSONObject object = new JSONObject();
                    object.put("time", DateUtil.getHourAndMinuteAndSecond(mDataList.get(0).getData_time()));
                    for (MData mData : mDataList) {
                        if (mData.getNorm_code() != null && mData.getNorm_val() != null) {
                            object.put(mData.getNorm_code(), mData.getNorm_val());
                        }
                    }
                    mdataArray.add(object);
                }
                dataJSON.put("count", map.size());
                dataJSON.put("data", mdataArray);
                mdataJSON.put("siteData", dataJSON);

                System.out.println(mdataJSON.toJSONString());
                return mdataJSON.toJSONString();
            }
            else {
                dataJSON.put("count", 0);
                dataJSON.put("data", "");
                mdataJSON.put("siteData", dataJSON);

                System.out.println(mdataJSON.toJSONString());
                return mdataJSON.toJSONString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map getMDataByStationsID() {
        String stationsID ="{ stations:[\"31010702330055\",\"31010702330053\"]}";
        //String json="{date:\"2018-09-07\",cars:[\"movingcar06\",\"movingcar05\"]}";
        //JSONArray station_json=JSONArray.fromObject(stationsID);
        //Map gatherQuery=JSONObject.parseObject(json);
        //JSONObject ao=jsarr.getJSONObject(0);
        //String stations=(String)gatherQuery.get("date");
        JSONObject jso= JSON.parseObject(stationsID);
        JSONArray stations=jso.getJSONArray("stations");
        System.out.println(stations.size());
        System.out.println(stations.get(0));
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("data",null);
        for(int i =0;i < stations.size(); i++ ){
       /* //"station_id":"1","station_name":"华师大", "L50": 107.7,
        List<MData> mDatas=dDataDao.getLatestMDataStations(stations.get(i));
        String station_id=stations.get(i)+"";
        Station station=stationDao.findByStationId(station_id);
        String station_name=station.getStationName();
        for (MData mdata:mDatas) {
            //mdata.getNorm_code()
        }*/
        }
        return resultMap;
    }

    @Override
    public Map getmanyMdatabystationanddata(Map params) {
        //String query="{"query":{"stations": ["31010702335001","31010702335002"],"time":"2018-10-30"}}"
        List<Norm> normList=normDao.getAllByMflag();
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

        //有一个问题，这个是对不同站点的同一个时间段的分钟数据进行对比，那么应该是判断整个操作超过一定时间就返回的吧
        for(String station:stationList){
            String station_id=station;
            Station station1=stationDao.findStationByStationId(station_id);
            String station_name=station1.getStationName();

            List<MData> innerDataList=mDataDao.getByStationAndHour(station_id,date);//获得分钟数据中的整点信息


            //System.out.println(innerDataList);
            if(innerDataList.isEmpty()) error_count++;
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            for(MData mData:innerDataList){
                String dateKey=sdf.format(mData.getData_time());
                //System.out.println("datekey:"+dateKey);
                String time=sdf2.format(mData.getData_time());
                if(innerMap.containsKey(dateKey)){
                    innerMap.get(dateKey).put(mData.getNorm_code(),mData.getNorm_val());
                }
                else{
                    Map<String,String> normVal=new HashMap<String,String>();
                    normVal.put("station_id",station_id);
                    normVal.put("station_name",station_name);
                    normVal.put("station_Sim",station1.getStationSim());
                    normVal.put("time",time);
                    normVal.put(mData.getNorm_code(),mData.getNorm_val());
                    innerMap.put(dateKey,normVal);
                }
            }
            for(int i=0;i<60;i++){
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
                }else if(i>=1&&i<=9){
                    if(!innerMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String, String>();
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",date.substring(date.length()-2)+":0"+i);
                        innerMap.put("0"+i,map);
                    }
                }else{
                    if(!innerMap.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String, String>();
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",date.substring(date.length()-2)+":"+i);
                        //System.out.println(date.substring(date.length()-2)+":"+i);
                        innerMap.put(""+i,map);
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
