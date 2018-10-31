package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.request.QueryDataByStationIdAndDatetimeReq;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
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
    @ApiImplicitParam(name = "query",value="包含站点id和查询时间的json",dataType = "String")
    @RequestMapping(value = "/getddatabystationanddate",method = RequestMethod.GET)
    public Map getDDataByStationAndDate(@RequestParam("query") String query){
        Map params=JSONObject.parseObject(query);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        String station_code=(String)params.get("station");
        String station_name=stationService.queryStatiionByCode(station_code).getStationName();
        String date=(String)params.get("date");
        List<DData> dDataList=dDataService.getByStationAndDate(station_code,date);
        List<Map> dataList=new ArrayList<Map>();
        Map <String,Map> dataMap=new HashMap<String,Map>();
        for(DData dData:dDataList){
            String time=dData.getData_time().toString();
            if(dataMap.containsKey(time)){
                dataMap.get(time).put(dData.getNorm_code(),dData.getNorm_val());
            }
            else{
                Map<String,String> innerMap=new HashMap<String,String>();
                innerMap.put("time",time);
                innerMap.put(dData.getNorm_code(),dData.getNorm_val());
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


    @ApiOperation(value="根据功能区划分查询符合的站点的实时数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area_id",value="功能区的id",dataType = "int"),
            @ApiImplicitParam(name = "each_page_num",value="分页的页大小",dataType = "int"),
            @ApiImplicitParam(name = "current_page",value="当前的页号",dataType = "int")
    })
    @RequestMapping(value = "/querymDataByStationsArea", method = RequestMethod.POST)
    public String querymDataByStationsArea(@RequestBody QuerymDataByStationsAreaReq querymDataByStationsAreaReq){

        return stationService.querymDataByStationArea(querymDataByStationsAreaReq);

    }

    @ApiOperation(value="根据功能区划分查询符合的站点的小时数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area_id",value="功能区的id",dataType = "int"),
            @ApiImplicitParam(name = "each_page_num",value="分页的页大小",dataType = "int"),
            @ApiImplicitParam(name = "current_page",value="当前的页号",dataType = "int")
    })
    @RequestMapping(value = "/queryhDataByStationsArea", method = RequestMethod.POST)
    public String queryhDataByStationArea(@RequestBody QueryhDataByStationAreaReq queryhDataByStationAreaReq){

        return stationService.queryhDataByStationArea(queryhDataByStationAreaReq);
    }


    @ApiOperation(value="根据功能区划分查询符合的站点的日数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area_id",value="功能区的id",dataType = "int"),
            @ApiImplicitParam(name = "each_page_num",value="分页的页大小",dataType = "int"),
            @ApiImplicitParam(name = "current_page",value="当前的页号",dataType = "int")
    })
    @RequestMapping(value = "/querydDataByStationsArea", method = RequestMethod.POST)
    public String querydDataByStationByArea(@RequestBody QuerydDataByStationAreaReq querydDataByStationAreaReq){

        return stationService.querydDataByStationArea(querydDataByStationAreaReq);
    }


    @ApiOperation(value="查询单站点指定时间的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "station_id",value="站点id",dataType = "String"),
            @ApiImplicitParam(name = "data_type",value="站点数据类型",dataType = "int"),
            @ApiImplicitParam(name = "date",value="给定的时间",dataType = "String")
    })
    @RequestMapping(value = "/queryDataByStationIdAndDatetime", method = RequestMethod.POST)
    public String queryDataByStationIdAndDatetime(@RequestBody QueryDataByStationIdAndDatetimeReq queryDataByStationIdAndDatetimeReq){

        return null;
    }
}
