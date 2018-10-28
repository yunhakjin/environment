package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
@Api("数据类api")
public class DataController {
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
        params=(Map)params.get("query");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        String station_code=(String)params.get("station");
        String station_name=stationService.queryStatiionByCode(station_code).getStationName();
        String date=(String)params.get("date");
        List<HData> hDataList=hDataService.getByStationAndDate(station_code,date);
        List<Map> dataList=new ArrayList<Map>();
        Map <String,Map> dataMap=new HashMap<String,Map>();
        for(HData hData:hDataList){
            String time=hData.getData_time().toString();
            if(dataMap.containsKey(time)){
                dataMap.get(time).put(hData.getNorm_code(),hData.getNorm_val());
            }
            else{
                Map<String,String> innerMap=new HashMap<String,String>();
                innerMap.put("time",time);
                innerMap.put(hData.getNorm_code(),hData.getNorm_val());
                //innnerMap.put("夜间值阈值",夜间值阈值)
                //innnerMap.put("昼间值阈值",昼间值阈值)
                dataMap.put(time,innerMap);
            }
        }
        for(Map value:dataMap.values()){
            dataList.add(value);
        }
        resultMap.put("station_name",station_name);
        resultMap.put("date",date);
        resultMap.put("data_list",dataList);
        return resultMap;
    }

    /*多站点指定日期小时数据查询*/
    @ApiOperation(value="多站点指定日期小时数据查询",notes = "需要传送包含站点id列表和查询时间的json")
    @ApiImplicitParam(name = "params",value="包含站点id和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getmanyhdatabystationanddate",method = RequestMethod.POST)
    public Map getManayHdataByStationAndDate(@RequestBody Map<String,Object> params){
        //String query="{"query":{"stations": ["31010702335001","31010702335002"],"time":"2018-10-27"}}"
        Map query=(Map)params.get("query");
        Map<String,Map> resultMap=new HashMap<String,Map>();
        List<String> stationList=(List)query.get("stations");
        String date=(String)query.get("time");
        List<Map> dataList=new ArrayList<Map>();
        int count=0;
        for(String station:stationList){
            String station_id=station;
            String station_name=stationService.queryStatiionByCode(station_id).getStationName();
            List<HData> innerDataList=hDataService.getByStationAndDate(station_id,date);
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            for(HData hData:innerDataList){
                String dateKey=hData.getData_time().toString();
                if(innerMap.containsKey(dateKey)){
                    innerMap.get(dateKey).put(hData.getNorm_code(),hData.getNorm_val());
                }
                else{
                    Map<String,String> normVal=new HashMap<String,String>();
                    normVal.put("station_id",station_id);
                    normVal.put("station_name",station_name);
                    normVal.put("time",dateKey);
                    normVal.put(hData.getNorm_code(),hData.getNorm_val());
                    innerMap.put(dateKey,normVal);
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
        Map query=(Map)params.get("query");
        Map<String,Map> resultMap=new HashMap<String,Map>();
        List<String> stationList=(List)query.get("stations");
        String date=(String)query.get("time");
        List<Map> dataList=new ArrayList<Map>();
        int count=0;
        for(String station:stationList){
            String station_id=station;
            String station_name=stationService.queryStatiionByCode(station_id).getStationName();
            List<DData> innerDataList=dDataService.getByStationAndDate(station_id,date);
            List<Map> innerList=new ArrayList<Map>();
            Map<String,Map> innerMap=new HashMap<String,Map>();
            for(DData dData:innerDataList){
                String dateKey=dData.getData_time().toString();
                if(innerMap.containsKey(dateKey)){
                    innerMap.get(dateKey).put(dData.getNorm_code(),dData.getNorm_val());
                }
                else{
                    Map<String,String> normVal=new HashMap<String,String>();
                    normVal.put("station_id",station_id);
                    normVal.put("station_name",station_name);
                    normVal.put("time",dateKey);
                    normVal.put(dData.getNorm_code(),dData.getNorm_val());
                    innerMap.put(dateKey,normVal);
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
}
