package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.Enum.DataTypeEnum;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.request.QueryDataByStationIdAndDatetimeReq;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.bean.*;
import com.springboot.environment.service.*;
import com.springboot.environment.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.util.TimeUtil;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
@Api("数据类api")
public class DataController {
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
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    MDataDao mDataDao;

    @Autowired
    StationDao stationDao;

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    /*单站点日昼夜数据查询*/
    @ApiOperation(value="单站点日昼夜数据查询",notes = "需要传送包含站点id和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getddatabystationanddate",method = RequestMethod.POST)
    public Map getDDataByStationAndDate(@RequestBody Map<String,Object> params){
        //String query={"query":{"station":"31010702335001","date":"2018-10-27"}}
        List<Norm> normList=normService.getAllByHflag();
        int count=0;
        SimpleDateFormat sdf=new SimpleDateFormat("HH");
        SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
        params=(Map)params.get("query");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        String station_code=(String)params.get("station");
        Station station=stationService.queryStatiionByCode(station_code);
        String station_name="";
        String d_limit="";
        String n_limit="";
        if(station!=null) {
            station_name=station.getStationName();
        }

        String date=(String)params.get("date");
        List<DData> dDataList=dDataService.getByStationAndDay(station_code,date);
        if(dDataList.isEmpty()){
            d_limit="56.99";
            n_limit="40.41";
        }
        else{
            for(DData dData:dDataList){
                String ddata_norm=dData.getNorm_code();
                if(ddata_norm.equals("n00008")){
                    d_limit=dData.getNorm_val();
                }
                else if(ddata_norm.equals("n00009")){
                    n_limit=dData.getNorm_val();
                }
            }
            if(d_limit==""){
                d_limit="56.99";
            }
            if(n_limit==""){
                n_limit="40.41";
            }
        }
        List<HData> hDataList=hDataService.getByStationAndDate(station_code,date);
        if(hDataList == null ||  hDataList.isEmpty()){
            return null;
        }
        List<Map> dataList=new ArrayList<Map>();
        Map <String,Map> dataMap=new HashMap<String,Map>();
        for(HData hData:hDataList){
            String timeKey=sdf.format(hData.getData_time());
//            String time=sdf2.format(hData.getData_time());
            if(dataMap.containsKey(timeKey)){
                dataMap.get(timeKey).put(hData.getNorm_code(),hData.getNorm_val());

            }
            else{
                Map<String,String> innerMap=new HashMap<String,String>();
                innerMap.put("time",timeKey);
                innerMap.put(hData.getNorm_code(),hData.getNorm_val());
                dataMap.put(timeKey,innerMap);
            }
        }
        for(int i=5;i<=16;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(i<10){
                if(!dataMap.containsKey("0"+i)){
                    tmpmap.put("dlimit",d_limit);
                    for(Norm norm:normList){
                        tmpmap.put(norm.getNorm_code(),"");
                    }
                    tmpmap.put("time",""+i);
                    dataMap.put("0"+i,tmpmap);
                }
                else{
                    dataMap.get("0"+i).put("dlimit",d_limit);
                }
            }
            else{
                if(!dataMap.containsKey(String.valueOf(i))){
                    tmpmap.put("dlimit",d_limit);
                    for(Norm norm:normList){
                        tmpmap.put(norm.getNorm_code(),"");
                    }
                    tmpmap.put("time",""+ i);
                    dataMap.put(String.valueOf(i),tmpmap);
                }
                else{
                    dataMap.get(String.valueOf(i)).put("dlimit",d_limit);
                }
            }
        }
        for(int i=0;i<=4;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(!dataMap.containsKey("0"+i)){
                tmpmap.put("nlimit",n_limit);
                for(Norm norm:normList){
                    tmpmap.put(norm.getNorm_code(),"");
                }
                tmpmap.put("time",""+i);
                dataMap.put("0"+i,tmpmap);
            }
            else{
                dataMap.get("0"+i).put("nlimit",n_limit);
            }
        }
        for(int i=17;i<24;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(!dataMap.containsKey(String.valueOf(i))){
                tmpmap.put("nlimit",n_limit);
                for(Norm norm:normList){
                    tmpmap.put(norm.getNorm_code(),"");
                }
                tmpmap.put("time",""+i);
                dataMap.put(String.valueOf(i),tmpmap);
            }
            else{
                dataMap.get(String.valueOf(i)).put("nlimit",n_limit);
            }
        }

        dataMap=dataMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
        for(Map value:dataMap.values()){
            dataList.add(value);
            count++;
        }
        resultMap.put("station_name",station_name);
        resultMap.put("date",date);
        resultMap.put("count",count);
        resultMap.put("data_list",dataList);
        return resultMap;
    }


    @ApiOperation(value="根据功能区划分查询符合的站点的实时数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="参数列表",dataType = "String")
    })
    @RequestMapping(value = "/querymDataByStationsArea", method = RequestMethod.POST)
    public String querymDataByStationsArea(@RequestBody Map<String, Object> params, HttpSession session) throws ParseException {

        return stationService.querymDataByStationArea(params, session);

    }

    @ApiOperation(value="根据功能区划分查询符合的站点的小时数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="参数列表",dataType = "String")
    })
    @RequestMapping(value = "/queryhDataByStationsArea", method = RequestMethod.POST)
    public String queryhDataByStationArea(@RequestBody Map<String, Object> params, HttpSession session){

        return stationService.queryhDataByStationArea(params, session);

    }


    @ApiOperation(value="根据功能区划分查询符合的站点的日数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="当前的页号",dataType = "String")
    })
    @RequestMapping(value = "/querydDataByStationsArea", method = RequestMethod.POST)
    public String querydDataByStationByArea(@RequestBody Map<String, Object> params, HttpSession session){

        return stationService.querydDataByStationArea(params, session);
    }


    @ApiOperation(value="查询单站点指定时间的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="参数列表",dataType = "String")
    })
    @RequestMapping(value = "/queryDataByStationIdAndDatetime", method = RequestMethod.POST)
    public String queryDataByStationIdAndDatetime(@RequestBody Map<String, Object> params, HttpSession session){

        System.out.println(params.toString());
        String stationId= params.get("station_code").toString();
        int dataType = (Integer) params.get("data_type");
        String date = params.get("time").toString();
//        User user =  (User) session.getAttribute("user");
//        System.out.println(user.toString());

        if (dataType == DataTypeEnum.MDATA.getCode()){
            return mDataService.queryMdataByStationIdAndDatetime(stationId, date);
        }
        else if(dataType == DataTypeEnum.M5DATA.getCode()){
            return m5DataService.queryM5dataByStationIdAndDatetime(stationId, date);
        }
        else if(dataType == DataTypeEnum.HDATA.getCode()){
            int offset = (Integer) params.get("offset");
            return hDataService.queryHdataByStationIdAndDatetime(stationId, date, offset);
        }
        else if(dataType == DataTypeEnum.DDATA.getCode()){
            int offset = (Integer) params.get("offset");
            return dDataService.queryDdataByStationIdAndDatetime(stationId, date, offset);
        }
        else {
            return null;
        }
    }

    /*多站点指定日期小时数据查询*/
    @ApiOperation(value="多站点指定日期小时数据查询",notes = "需要传送包含站点id列表和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyhdatabystationanddate",method = RequestMethod.POST)
    public Map getManyHdataByStationAndDate(@RequestBody Map<String,Object> params){
        //String query="{"query":{"stations": ["31010702335001","31010702335002"],"time":"2018-10-30"}}"
        List<Norm> normList=normService.getAllByHflag();
        Map query=(Map)params.get("query");
        Map<String,Map> resultMap=new HashMap<String,Map>();
        List<String> stationList=(List)query.get("stations");
        String date=(String)query.get("time");
        List<Map> dataList=new ArrayList<Map>();
        SimpleDateFormat sdf=new SimpleDateFormat("HH");
        SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm:ss");
        int count=0;
        int error_count=0;
        for(String station:stationList){
            String station_id=station;
            Station station1=stationService.queryStatiionByCode(station_id);
            String station_name=station1.getStationName();
            List<HData> innerDataList=hDataService.getByStationAndDate(station_id,date);
            if(innerDataList == null || innerDataList.isEmpty()) error_count++;
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            if (!StringUtil.isNullOrEmpty(innerDataList)) {
                for (HData hData : innerDataList) {
                    String dateKey = sdf.format(hData.getData_time());
                    String time = sdf2.format(hData.getData_time());
                    if (innerMap.containsKey(dateKey)) {
                        innerMap.get(dateKey).put(hData.getNorm_code(), hData.getNorm_val());
                    } else {
                        Map<String, String> normVal = new HashMap<String, String>();
                        normVal.put("station_id", station_id);
                        normVal.put("station_name", station_name);
                        normVal.put("station_Sim", station1.getStationSim());
                        normVal.put("time", time);
                        normVal.put(hData.getNorm_code(), hData.getNorm_val());
                        innerMap.put(dateKey, normVal);
                    }
                }
            }
            for(int i=0;i<10;i++){
                if(!innerMap.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("station_id",station_id);
                    map.put("station_name",station_name);
                    map.put("station_Sim",station1.getStationSim());
                    map.put("time","0"+i+":00:00");
                    innerMap.put("0"+i,map);
                }
            }
            for(int i=10;i<24;i++){
                if(!innerMap.containsKey(String.valueOf(i))){
                    Map<String,String> map=new HashMap<String, String>();
                    for(Norm norm:normList){
                        map.put(norm.getNorm_code(),"");
                    }
                    map.put("station_id",station_id);
                    map.put("station_name",station_name);
                    map.put("station_Sim",station1.getStationSim());
                    map.put("time",i+":00:00");
                    innerMap.put(String.valueOf(i),map);
                }
            }
            innerMap=innerMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                     .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:innerMap.values()){
                innerList.add(value);
                count++;
            }
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("time",date);
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

    /*多站点指定日期日数据查询*/
    @ApiOperation(value="多站点指定日期日数据查询",notes = "需要传送包含站点id列表和查询时间(月份)的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyddatabystationanddate",method = RequestMethod.POST)
    public Map getManyDdataByStationAndDate(@RequestBody Map<String,Object> params){
        //String query="{"query":{"stations": ["31010702335001","31010702335002"],"time":"2018-10"}}"
        List<Norm> normList=normService.getAllByDflag();
        Map query=(Map)params.get("query");
        Map<String,Map> resultMap=new HashMap<String,Map>();
        List<String> stationList=(List)query.get("stations");
        String querytime=(String)query.get("time");
        List<Map> dataList=new ArrayList<Map>();
        int count=0;
        SimpleDateFormat sdf=new SimpleDateFormat("dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        String year=querytime.split("-")[0];
        String month=querytime.split("-")[1];
        int error_count=0;
        for(String station:stationList){
            String station_id=station;
            Station station1=stationService.queryStatiionByCode(station_id);
            String station_name=station1.getStationName();
            List<DData> innerDataList=dDataService.getByStationAndMonth(station_id,querytime);
            if(innerDataList.isEmpty()) error_count++;
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            for(DData dData:innerDataList){
                String dateKey=sdf.format(dData.getData_time());
                if(innerMap.containsKey(dateKey)){
                    innerMap.get(dateKey).put(dData.getNorm_code(),dData.getNorm_val());
                }
                else{
                    Map<String,String> normVal=new HashMap<String,String>();
                    normVal.put("station_id",station_id);
                    normVal.put("station_name",station_name);
                    normVal.put("station_Sim",station1.getStationSim());
                    normVal.put("time",sdf2.format(dData.getData_time()));
                    normVal.put(dData.getNorm_code(),dData.getNorm_val());
                    innerMap.put(dateKey,normVal);
                }
            }
            if(month.equals("02")){
                if(Integer.valueOf(year)%4==0){
                    for(int i=1;i<10;i++){
                        if(!innerMap.containsKey("0"+i)){
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("station_id",station_id);
                            map.put("station_name",station_name);
                            map.put("station_Sim",station1.getStationSim());
                            map.put("time",year+"-"+month+"-"+"0"+i);
                            for(Norm norm:normList){
                                map.put(norm.getNorm_code(),"");
                            }
                            innerMap.put("0"+i,map);
                        }
                    }
                    for(int i=10;i<30;i++){
                        if(!innerMap.containsKey(String.valueOf(i))){
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("station_id",station_id);
                            map.put("station_name",station_name);
                            map.put("station_Sim",station1.getStationSim());
                            map.put("time",year+"-"+month+"-"+i);
                            for(Norm norm:normList){
                                map.put(norm.getNorm_code(),"");
                            }
                            innerMap.put(String.valueOf(i),map);
                        }
                    }
                }
                else{
                    for(int i=1;i<10;i++){
                        if(!innerMap.containsKey("0"+i)){
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("station_id",station_id);
                            map.put("station_name",station_name);
                            map.put("station_Sim",station1.getStationSim());
                            map.put("time",year+"-"+month+"-"+"0"+i);
                            for(Norm norm:normList){
                                map.put(norm.getNorm_code(),"");
                            }
                            innerMap.put("0"+i,map);
                        }
                    }
                    for(int i=10;i<29;i++){
                        if(!innerMap.containsKey(String.valueOf(i))){
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("station_id",station_id);
                            map.put("station_name",station_name);
                            map.put("station_Sim",station1.getStationSim());
                            map.put("time",year+"-"+month+"-"+i);
                            for(Norm norm:normList){
                                map.put(norm.getNorm_code(),"");
                            }
                            innerMap.put(String.valueOf(i),map);
                        }
                    }
                }
            }
            else if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")){
                for(int i=1;i<10;i++){
                    if(!innerMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",year+"-"+month+"-"+"0"+i);
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        innerMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<32;i++){
                    if(!innerMap.containsKey(String.valueOf(i))){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",year+"-"+month+"-"+i);
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        innerMap.put(String.valueOf(i),map);
                    }
                }
            }
            else{
                for(int i=1;i<10;i++){
                    if(!innerMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",year+"-"+month+"-"+"0"+i);
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        innerMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<31;i++){
                    if(!innerMap.containsKey(String.valueOf(i))){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station_id);
                        map.put("station_name",station_name);
                        map.put("station_Sim",station1.getStationSim());
                        map.put("time",year+"-"+month+"-"+i);
                        for(Norm norm:normList){
                            map.put(norm.getNorm_code(),"");
                        }
                        innerMap.put(String.valueOf(i),map);
                    }
                }
            }
            innerMap=innerMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newVvalue)->oldValue,LinkedHashMap::new));
            for(Map value:innerMap.values()){
                innerList.add(value);
                count++;
            }
            Map<String,Object> tmp=new HashMap<String,Object>();
            tmp.put("time",querytime);
            tmp.put("data",innerList);
            dataList.add(tmp);
        }
        if(error_count==stationList.size()){
            return null;
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("datas",dataList);
        resultMap.put("stationData",map);
        return resultMap;
    }


    /*
    * 选择4到10个站点之后点击查询，然后返回这几个站点的最新一条实时数据（分钟） 比较页1
    * */
    @GetMapping("getMDataByStationsID")
    public Map<String,Object> getMDataByStationsID(){
        return mDataService.getMDataByStationsID();
    }

    /*
    * （单站点月数据对比查询）根据查询粒度返回需要的站点数据，（年，月，天） 比较页2---月数据--返回30天
    * */
    @ApiOperation(value = "根据查询粒度返回需要的站点月数据",notes = "比较页2---月数据--返回30天")
    @ApiImplicitParam(name = "params",value="包含查询站点的id和查询的月份json",dataType = "JSON")
    @RequestMapping(value = "/getStationsData",method = RequestMethod.POST)
    public Map getStationsData(@RequestBody Map<String,Object> params){
        return dDataService.getStationsData(params);
    }

    /*
    * （单站点日数据对比查询）根据查询粒度返回需要的站点数据，（年，月，天） 比较页2--天数据--返回24个小时
    * */
    @ApiOperation(value = "根据查询粒度返回需要的站点日数据",notes = "比较页2---日数据--返回24个小时")
    @ApiImplicitParam(name = "params",value="包含查询站点的id和查询的日期json",dataType = "JSON")
    @RequestMapping(value = "/getStationsDataByDays",method = RequestMethod.POST)
    public Map<String,Object> getStationsDataByDays(@RequestBody Map<String,Object> params){
        //return dDataService.getStationsDataByDays();
        return hDataService.getStationsDataByDays(params);
    }

    /*
    * （单站点5分钟数据对比查询）根据查询粒度返回需要的站点数据，（年，月，天）
    * */
    @ApiOperation(value = "单站点5分钟数据对比",notes = "单站点不同5分钟数据对比-返回120条")
    @ApiImplicitParam(name = "params",value="包含查询站点的id和查询的小时json",dataType = "JSON")
    @RequestMapping(value = "/getM5StationsData",method = RequestMethod.POST)
    public Map getM5StationsData(@RequestBody Map<String,Object> params){
        return m5DataService.getM5StationsData(params);
    }

    /*多站点指定日期5分钟数据查询*/
    @ApiOperation(value="多站点指定日期5分钟数据查询",notes = "需要传送包含站点id列表和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyM5databystationanddate",method = RequestMethod.POST)
    public Map getmanyM5databystationanddate(@RequestBody Map<String,Object> params){
       return m5DataService.getmanyM5databystationanddata(params);
    }

    //分钟数据对比查询超时的标志，flag=1 超时  flag=0 未超时
    //int flag=0;
    /*多站点指定日期分钟数据查询*/
    @ApiOperation(value="多站点指定日期分钟数据查询",notes = "需要传送包含站点id列表和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyMdatabystationanddate",method = RequestMethod.POST)
    public Map getmanyMdatabystationanddate(@RequestBody Map<String,Object> params){
        /*Timer timer = new Timer();
        Map res =new HashMap<>();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//这里相当于延迟20000ms执行此程序
                flag = 1;
                //timer.cancel();//关闭线程
                System.gc();
            }
        },30000);
        Map map=mDataService.getmanyMdatabystationanddata(params);
        System.out.println("flag:"+flag);
        if(flag==0){
            map.put("queryFlag","true");
            return map;
        }else {
            Map mapTimeout=new HashMap<>();
            mapTimeout.put("queryFlag","false");
            return mapTimeout;
        }*/
        return mDataService.getmanyMdatabystationanddata(params);

    }
    @ApiOperation(value = "测试redis数据",notes = "测试redis数据")
    @ApiImplicitParam(name = "params",value="向redis中写入2018-11-16的所有站点数据，约16W条",dataType = "JSON")
    @RequestMapping(value = "/testRedis",method = RequestMethod.POST)
    public String testRedis(){

        long start = System.currentTimeMillis();
        List<MData> allMdata = mDataDao.getMdataByDay("2018-11-16 00:00:00", "2018-11-16 23:59:59");

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        for (MData mData : allMdata){

            String stationId = mData.getStation_id();
            double score = (double)mData.getData_time().getTime();

            JSONObject mdataJson = new JSONObject();
            mdataJson.put("data_id", mData.getData_id());
            //这里的redis存储的是时间戳
            mdataJson.put("data_time", mData.getData_time());
            mdataJson.put("station_id", mData.getStation_id());
            mdataJson.put("norm_code", mData.getNorm_code());
            mdataJson.put("norm_val", mData.getNorm_val());
            String dataString = mdataJson.toJSONString();

            zset.add(stationId, dataString, score);
        }

        System.out.println("当前耗时" + (int)(System.currentTimeMillis() - start) / 1000 );

        return "当前耗时" + (int)(System.currentTimeMillis() - start) / 1000 ;
    }


}
