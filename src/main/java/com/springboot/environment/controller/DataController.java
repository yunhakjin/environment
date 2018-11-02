package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.Enum.DataTypeEnum;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.request.QueryDataByStationIdAndDatetimeReq;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.bean.*;
import com.springboot.environment.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /*查询指定站点，指定时间的日数据
    * page:起始页
    * size:每一页的数据条数*/
    @ApiOperation(value="查询指定站点，指定时间的日数据",notes = "需要定义站点id,查询的起止时间，以及数据状态，暂时默认都为1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "station_id",value="站点id",dataType = "String"),
            @ApiImplicitParam(name = "starttime",value="查询开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endtime",value="查询结束时间",dataType = "String"),
            @ApiImplicitParam(name = "data_check",value="数据是否审核",dataType = "int"),
            @ApiImplicitParam(name = "data_status",value="数据状态是否在线",dataType = "int"),
            @ApiImplicitParam(name = "page",value="起始页",dataType = "int"),
            @ApiImplicitParam(name = "size",value="总的页数",dataType = "int")
    }
    )
    @GetMapping("getDDataByStationAndDate/{station_id}/{start}/{end}")
    public Page<DData> getDDataByStationAndDate(@PathVariable String station_id,
                                           @PathVariable String start,
                                           @PathVariable String end){
        return dDataService.getByStationAndTime(station_id,start,end,1,1,0,2);
    }

    /*查询指定站点，指定时间的小时数据
     * page:起始页
     * size:每一页的数据条数*/
    @ApiOperation(value="查询指定站点，指定时间的小时数据",notes = "需要定义站点id,查询的起止时间，以及数据状态，暂时默认都为1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "station_id",value="站点id",dataType = "String"),
            @ApiImplicitParam(name = "starttime",value="查询开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endtime",value="查询结束时间",dataType = "String"),
            @ApiImplicitParam(name = "data_check",value="数据是否审核",dataType = "int"),
            @ApiImplicitParam(name = "data_status",value="数据状态是否在线",dataType = "int"),
            @ApiImplicitParam(name = "page",value="起始页",dataType = "int"),
            @ApiImplicitParam(name = "size",value="总的页数",dataType = "int")
    }
    )
    @GetMapping("getHDataByStationAndDate/{station_id}/{start}/{end}")
    public Page<HData> getHDataByStationAndDate(@PathVariable String station_id,
                                           @PathVariable String start,
                                           @PathVariable String end){
        return hDataService.getByStationAndTime(station_id,start,end,1,1,0,2);
    }

    /*查询指定站点，指定时间的五分钟数据
     * page:起始页
     * size:每一页的数据条数*/
    @ApiOperation(value="查询指定站点，指定时间的五分钟数据",notes = "需要定义站点id,查询的起止时间，以及数据状态，暂时默认都为1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "station_id",value="站点id",dataType = "String"),
            @ApiImplicitParam(name = "starttime",value="查询开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endtime",value="查询结束时间",dataType = "String"),
            @ApiImplicitParam(name = "data_check",value="数据是否审核",dataType = "int"),
            @ApiImplicitParam(name = "data_status",value="数据状态是否在线",dataType = "int"),
            @ApiImplicitParam(name = "page",value="起始页",dataType = "int"),
            @ApiImplicitParam(name = "size",value="总的页数",dataType = "int")
    }
    )
    @GetMapping("getM5DataByStationAndDate/{station_id}/{start}/{end}")
    public Page<M5Data> getM5DataByStationAndDate(@PathVariable String station_id,
                                                  @PathVariable String start,
                                                  @PathVariable String end){
        return m5DataService.getByStationAndTime(station_id,start,end,1,1,0,2);
    }

    /*查询指定站点，指定时间的实时数据
     * page:起始页
     * size:每一页的数据条数*/
    @ApiOperation(value="查询指定站点，指定时间的实时数据",notes = "需要定义站点id,查询的起止时间，以及数据状态，暂时默认都为1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "station_id",value="站点id",dataType = "String"),
            @ApiImplicitParam(name = "starttime",value="查询开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endtime",value="查询结束时间",dataType = "String"),
            @ApiImplicitParam(name = "data_check",value="数据是否审核",dataType = "int"),
            @ApiImplicitParam(name = "data_status",value="数据状态是否在线",dataType = "int"),
            @ApiImplicitParam(name = "page",value="起始页",dataType = "int"),
            @ApiImplicitParam(name = "size",value="总的页数",dataType = "int")
    }
    )
    @GetMapping("getMDataByStationAndDate/{station_id}/{start}/{end}")
    public Page<MData> getMDataByStationAndDate(@PathVariable String station_id,
                                                @PathVariable String start,
                                                @PathVariable String end){
        return mDataService.getByStationAndTime(station_id,start,end,1,1,0,10);
    }

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
        String station_name=stationService.queryStatiionByCode(station_code).getStationName();
        String date=(String)params.get("date");
        List<HData> hDataList=hDataService.getByStationAndDate(station_code,date);
        List<Map> dataList=new ArrayList<Map>();
        Map <String,Map> dataMap=new HashMap<String,Map>();
        List<DData> dDataList=dDataService.getByStationAndDay(station_code,date);
        String dlimit="";
        String nlimit="";
        for(DData dData:dDataList){
            if(dData.getNorm_code().equals("n00008")){
                dlimit=dData.getNorm_val();
            }
            else if(dData.getNorm_code().equals("n00009")){
                nlimit=dData.getNorm_val();
            }
       }
        for(HData hData:hDataList){
            String timeKey=sdf.format(hData.getData_time());
            String time=sdf2.format(hData.getData_time());
            if(dataMap.containsKey(timeKey)){
                dataMap.get(timeKey).put(hData.getNorm_code(),hData.getNorm_val());
            }
            else{
                Map<String,String> innerMap=new HashMap<String,String>();
                innerMap.put("time",time);
                innerMap.put(hData.getNorm_code(),hData.getNorm_val());
                dataMap.put(timeKey,innerMap);
            }
        }
        for(int i=4;i<=16;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(i<10){
                if(!dataMap.containsKey("0"+i)){
                    tmpmap.put("dlimit",dlimit);
                    for(Norm norm:normList){
                        tmpmap.put(norm.getNorm_code(),"");
                    }
                    tmpmap.put("time","0"+i+":00:00");
                    dataMap.put("0"+i,tmpmap);
                }
                else{
                    dataMap.get("0"+i).put("dlimit",dlimit);
                }
            }
            else{
                if(!dataMap.containsKey(String.valueOf(i))){
                    tmpmap.put("dlimit",dlimit);
                    for(Norm norm:normList){
                        tmpmap.put(norm.getNorm_code(),"");
                    }
                    tmpmap.put("time",i+":00:00");
                    dataMap.put(String.valueOf(i),tmpmap);
                }
                else{
                    dataMap.get(String.valueOf(i)).put("dlimit",dlimit);
                }
            }
        }
        for(int i=0;i<=4;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(!dataMap.containsKey("0"+i)){
                tmpmap.put("nlimit",nlimit);
                for(Norm norm:normList){
                    tmpmap.put(norm.getNorm_code(),"");
                }
                tmpmap.put("time","0"+i+":00:00");
                dataMap.put("0"+i,tmpmap);
            }
            else{
                dataMap.get("0"+i).put("nlimit",nlimit);
            }
        }
        for(int i=17;i<24;i++){
            Map<String,String> tmpmap=new HashMap<String, String>();
            if(!dataMap.containsKey(String.valueOf(i))){
                tmpmap.put("nlimit",nlimit);
                for(Norm norm:normList){
                    tmpmap.put(norm.getNorm_code(),"");
                }
                tmpmap.put("time",i+":00:00");
                dataMap.put(String.valueOf(i),tmpmap);
            }
            else{
                dataMap.get(String.valueOf(i)).put("nlimit",nlimit);
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
    public String querymDataByStationsArea(@RequestBody Map<String, Object> params){

        System.out.println(params.toString());
        int area = (Integer)params.get("area_id");
        int pageSize = (Integer) params.get("each_page_num");
        int pageNum = (Integer) params.get("current_page");

        QuerymDataByStationsAreaReq querymDataByStationsAreaReq = new QuerymDataByStationsAreaReq(area, pageSize, pageNum);
        return stationService.querymDataByStationArea(querymDataByStationsAreaReq);

    }

    @ApiOperation(value="根据功能区划分查询符合的站点的小时数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="参数列表",dataType = "String")
    })
    @RequestMapping(value = "/queryhDataByStationsArea", method = RequestMethod.POST)
    public String queryhDataByStationArea(@RequestBody Map<String, Object> params){

        System.out.println(params.toString());
        int area = (Integer)params.get("area_id");
        int pageSize = (Integer) params.get("each_page_num");
        int pageNum = (Integer) params.get("current_page");

        QueryhDataByStationAreaReq queryhDataByStationAreaReq = new QueryhDataByStationAreaReq(area, pageSize, pageNum);
        return stationService.queryhDataByStationArea(queryhDataByStationAreaReq);
    }


    @ApiOperation(value="根据功能区划分查询符合的站点的日数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="当前的页号",dataType = "String")
    })
    @RequestMapping(value = "/querydDataByStationsArea", method = RequestMethod.POST)
    public String querydDataByStationByArea(@RequestBody Map<String, Object> params){

        System.out.println(params.toString());
        int area = (Integer)params.get("area_id");
        int pageSize = (Integer) params.get("each_page_num");
        int pageNum = (Integer) params.get("current_page");

        QuerydDataByStationAreaReq querydDataByStationAreaReq = new QuerydDataByStationAreaReq(area, pageSize, pageNum);

        return stationService.querydDataByStationArea(querydDataByStationAreaReq);
    }


    @ApiOperation(value="查询单站点指定时间的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="参数列表",dataType = "String")
    })
    @RequestMapping(value = "/queryDataByStationIdAndDatetime", method = RequestMethod.POST)
    public String queryDataByStationIdAndDatetime(@RequestBody Map<String, Object> params){

        System.out.println(params.toString());
        String stationId= params.get("station_id").toString();
        int dataType = (Integer) params.get("data_type");
        String date = params.get("date").toString();

        if (dataType == DataTypeEnum.MDATA.getCode()){
            return mDataService.queryMdataByStationIdAndDatetime(stationId, date);
        }
        else if(dataType == DataTypeEnum.M5DATA.getCode()){
            return m5DataService.queryM5dataByStationIdAndDatetime(stationId, date);
        }
        else if(dataType == DataTypeEnum.HDATA.getCode()){
            return hDataService.queryHdataByStationIdAndDatetime(stationId, date);
        }
        else if(dataType == DataTypeEnum.DDATA.getCode()){
            return dDataService.queryDdataByStationIdAndDatetime(stationId, date);
        }
        else {
            return null;
        }
    }

    /*多站点指定日期小时数据查询*/
    @ApiOperation(value="多站点指定日期小时数据查询",notes = "需要传送包含站点id列表和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyhdatabystationanddate",method = RequestMethod.POST)
    public Map getManayHdataByStationAndDate(@RequestBody Map<String,Object> params){
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
        for(String station:stationList){
            String station_id=station;
            String station_name=stationService.queryStatiionByCode(station_id).getStationName();
            List<HData> innerDataList=hDataService.getByStationAndDate(station_id,date);
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            for(HData hData:innerDataList){
                String dateKey=sdf.format(hData.getData_time());
                String time=sdf2.format(hData.getData_time());
                if(innerMap.containsKey(dateKey)){
                    innerMap.get(dateKey).put(hData.getNorm_code(),hData.getNorm_val());
                }
                else{
                    Map<String,String> normVal=new HashMap<String,String>();
                    normVal.put("station_id",station_id);
                    normVal.put("station_name",station_name);
                    normVal.put("time",time);
                    normVal.put(hData.getNorm_code(),hData.getNorm_val());
                    innerMap.put(dateKey,normVal);
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
    public Map getManayDdataByStationAndDate(@RequestBody Map<String,Object> params){
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
        for(String station:stationList){
            String station_id=station;
            String station_name=stationService.queryStatiionByCode(station_id).getStationName();
            List<DData> innerDataList=dDataService.getByStationAndMonth(station_id,querytime);
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
    * 根据查询粒度返回需要的站点数据，（年，月，天） 比较页2---月数据--返回30天
    * */
    @ApiOperation(value = "根据查询粒度返回需要的站点月数据",notes = "比较页2---月数据--返回30天")
    @ApiImplicitParam(name = "params",value="包含查询站点的id和查询的月份json",dataType = "JSON")
    @RequestMapping(value = "/getStationsData",method = RequestMethod.POST)
    public Map getStationsData(@RequestBody Map<String,Object> params){
        return dDataService.getStationsData(params);
    }

    /*
    * 根据查询粒度返回需要的站点数据，（年，月，天） 比较页2--天数据--返回24个小时
    * */
    @ApiOperation(value = "根据查询粒度返回需要的站点日数据",notes = "比较页2---日数据--返回24个小时")
    @ApiImplicitParam(name = "params",value="包含查询站点的id和查询的月份json",dataType = "JSON")
    @RequestMapping(value = "/getStationsDataByDays",method = RequestMethod.POST)
    public Map<String,Object> getStationsDataByDays(@RequestBody Map<String,Object> params){
        //return dDataService.getStationsDataByDays();
        return hDataService.getStationsDataByDays(params);
    }

}
