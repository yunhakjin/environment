package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.dao.NormDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.DDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DDataServiceImp implements DDataService {

    @Autowired
    private DDataDao dDataDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private NormDao normDao;

    @Override
    public List<DData> getAll() {
        return dDataDao.findAll();
    }

    @Override
    public Page<DData> getAllPage(int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        return dDataDao.findAll(pageable);
    }

    @Override
    public List<DData> getDDataByData_id(String Data_id) {
          return dDataDao.getAllByData_id(Data_id);
    }

    @Override
    public void saveDData(DData dData) {
        dDataDao.saveAndFlush(dData);
    }

    @Override
    public void delDData(String Data_id) {
        dDataDao.deleteAll(getDDataByData_id(Data_id));
    }

    @Override
    public Page<DData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return dDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public List<DData> getByStationAndMonth(String station_id, String month) {
        return dDataDao.getByStationAndMonth(station_id,month);
    }

    @Override
    public List<DData> getByStationAndDay(String station_id, String date) {
        return dDataDao.getByStationAndDay(station_id,date);
    }


      /*
       * 月数据查询：返回所查的单站点两个月份的三十天的指标对比
       * */
    @Override
    public Map getStationsData(Map<String, Object> params) {
        //String stationRequest = "{station_id:\"31010702330055\", type:2,  timeRange:{ time1:\"2018-08\",   time2:\"2018-09\"   }}";
        String s=params+"";
        System.out.println(s);

        Map<String,String> timeRange=(Map<String, String>) params.get("timeRange");
        String station_id=params.get("station_id")+"";
        String type=params.get("type")+"";

        String time1=timeRange.get("time1")+"";
        String time2=timeRange.get("time2")+"";


        String year1=time1.split("-")[0];
        String month1=time1.split("-")[1];

        String year2=time2.split("-")[0];
        String month2=time2.split("-")[1];

        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        System.out.println(station_id);
        Station station=stationDao.findStationByStationId(station_id);
        System.out.println(station);
        String station_name= station.getStationName();

        List<DData> dDatas_time1=dDataDao.getByStationAndMonth(station_id,time1);
        List<DData> dDatas_time2=dDataDao.getByStationAndMonth(station_id,time2);
        System.out.println(dDatas_time1);
        Map<String, Object> time1Map = new LinkedHashMap<String, Object>();//time1
        Map<String, Object> time2Map = new LinkedHashMap<String, Object>();//time2
        time1Map.put("time",time1);
        time2Map.put("time",time2);
        SimpleDateFormat sdf=new SimpleDateFormat("dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf3=new SimpleDateFormat("MM");

        Map<String,Map> innertrackMap=new HashMap<String,Map>();
        Map<String,Map> innertrack2Map=new HashMap<String,Map>();
        for(int i=0;i<dDatas_time1.size();i++){
            //String trackTime=dDatas_time1.get(i).getData_time().toString().substring(0,10);
            String trackTime=sdf.format(dDatas_time1.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrackMap.containsKey(trackTime)){
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",sdf2.format(dDatas_time1.get(i).getData_time()));
                System.out.println(dDatas_time1.get(i).getData_time());
                System.out.println(sdf2.format(dDatas_time1.get(i).getData_time()));
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.put(trackTime,normVal);
            }
        }
        for(int i=0;i<dDatas_time2.size();i++){
            //String trackTime=dDatas_time2.get(i).getData_time().toString().substring(0,10);
            String trackTime=sdf.format(dDatas_time2.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrack2Map.containsKey(trackTime)){
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",sdf2.format(dDatas_time2.get(i).getData_time()));
                System.out.println(dDatas_time2.get(i).getData_time());
                System.out.println(sdf2.format(dDatas_time2.get(i).getData_time()));
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.put(trackTime,normVal);
            }
        }
        System.out.println(innertrack2Map);
        List<Map> innerTrackList=new ArrayList<Map>();
        List<Map> innerTrackList2=new ArrayList<Map>();

        List<Norm> normList1=normDao.getAllByDflag();
        List<Norm> normList2=normDao.getAllByDflag();
        if(month1.equals("02")){
            if(Integer.valueOf(year1)%4==0){
                for(int i=1;i<10;i++){
                    if(!innertrackMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+"0"+i+"");
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<30;i++){
                    if(!innertrackMap.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put(String.valueOf(i),map);
                    }
                }
            }
            else{
                for(int i=1;i<10;i++){
                    if(!innertrackMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+"0"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<29;i++){
                    if(!innertrackMap.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put(String.valueOf(i),map);
                    }
                }
            }
        }
        else if(month1.equals("01")||month1.equals("03")||month1.equals("05")||month1.equals("07")||month1.equals("08")||month1.equals("10")||month1.equals("12")){
            for(int i=1;i<10;i++){
                if(!innertrackMap.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+"0"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put("0"+i,map);
                }
            }
            for(int i=10;i<32;i++){
                if(!innertrackMap.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put(String.valueOf(i),map);
                }
            }
        }
        else{
            for(int i=1;i<10;i++){
                if(!innertrackMap.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+"0"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put("0"+i,map);
                }
            }
            for(int i=10;i<31;i++){
                if(!innertrackMap.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put(String.valueOf(i),map);
                }
            }
        }
//=======================================================================

        if(month2.equals("02")){
            if(Integer.valueOf(year2)%4==0){
                for(int i=1;i<10;i++){
                    if(!innertrack2Map.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+"0"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put("0"+i,map);
                    }
                }
                for(int i=10;i<30;i++){
                    if(!innertrack2Map.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put(String.valueOf(i),map);
                    }
                }
            }
            else{
                for(int i=1;i<10;i++){
                    if(!innertrack2Map.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+"0"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put("0"+i,map);
                    }
                }
                for(int i=10;i<29;i++){
                    if(!innertrack2Map.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put(String.valueOf(i),map);
                    }
                }
            }
        }
        else if(month2.equals("01")||month2.equals("03")||month2.equals("05")||month2.equals("07")||month2.equals("08")||month2.equals("10")||month2.equals("12")){
            for(int i=1;i<10;i++){
                if(!innertrack2Map.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+"0"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put("0"+i,map);
                }
            }
            for(int i=10;i<32;i++){
                if(!innertrack2Map.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put(String.valueOf(i),map);
                }
            }
        }
        else{
            for(int i=1;i<10;i++){
                if(!innertrack2Map.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+"0"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put("0"+i,map);
                }
            }
            for(int i=10;i<31;i++){
                if(!innertrack2Map.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put(String.valueOf(i),map);
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
}
