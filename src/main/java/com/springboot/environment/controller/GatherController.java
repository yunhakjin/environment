package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Gather;
import com.springboot.environment.bean.GatherData;
import com.springboot.environment.service.GatherDataService;
import com.springboot.environment.service.GatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/gather")
@Api("采集车以及采集车数据api")
public class GatherController {
    @Autowired
    private GatherService gatherService;

    @Autowired
    private GatherDataService gatherDataService;

    @ApiOperation(value = "返回所有的采集车信息",notes = "所有采集车信息")
    @RequestMapping(value = "/getallgather",method = RequestMethod.GET)
    public Map getAllGather(){
        List<Gather> gatherList= gatherService.getAllGather();
        Map<String,List> carMap=new HashMap<String,List>();
        List<Map> carList=new ArrayList<>();
        carMap.put("carList",carList);
        for(int i=0;i<gatherList.size();i++){
            Map<String,String> gather=new HashMap<String,String>();
            gather.put("carName",gatherList.get(i).getGather_id());
            carList.add(gather);
        }
//        String result=JSONObject.toJSONString(carMap);
////        return result;
        return carMap;
    }

    @ApiOperation(value = "根据采集车id返回其所有轨迹",notes = "根据采集车id返回其所有轨迹")
    @ApiImplicitParam(name = "params",value="包含采集车id列表和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getgatherdata",method = RequestMethod.POST)
    public Map getGatherDataByGatherId(@RequestBody Map<String,Object> params){
//        String json="{query:{date:"2018-09-07",cars:["movingcar06","movingcar05"]}}";
//        Map gatherQuery=JSONObject.parseObject(query);
        Map gatherQuery=(Map)params.get("query");
        String date=(String)gatherQuery.get("date");
        List<String> cars=(List)gatherQuery.get("cars");
        Map<String,List> result=new HashMap<String, List>();
        List<Map> trackList=new ArrayList<Map>();
        for(int i=0;i<cars.size();i++){
            Map<String,Object> trackMap=new HashMap<String,Object>();
            String car=cars.get(i);
            trackMap.put("carName",car);
            List<GatherData> gatherDataList=gatherDataService.getAllByGather_idAndData_time(car,date);
            Map<String,Map> innertrackMap=new HashMap<String,Map>();
            for(int j=0;j<gatherDataList.size();j++){
                String trackTime=gatherDataList.get(j).getData_time().toString();
                Map<String,Object> normVal=new HashMap<String,Object>();
                if(innertrackMap.containsKey(trackTime)){
                    normVal.put(gatherDataList.get(j).getNorm_code(),gatherDataList.get(j).getNorm_val());
                    innertrackMap.get(trackTime).putAll(normVal);
                }
                else{
                    normVal.put("time",trackTime);
                    List<String> position= Arrays.asList(gatherDataList.get(j).getGather_position().replace('(',' ').replace(')',' ').split(","));
//                    List<Integer> pos=new ArrayList<Integer>();
//                    pos.add(Integer.valueOf(position.get(0)));
//                    pos.add(Integer.valueOf(position.get(1)));
                    normVal.put("position",position);
                    normVal.put(gatherDataList.get(j).getNorm_code(),gatherDataList.get(j).getNorm_val());
                    innertrackMap.put(trackTime,normVal);
                }
            }
            List<Map> innerTrackList=new ArrayList<Map>();
            for(Map value:innertrackMap.values()){
                innerTrackList.add(value);
            }
            trackMap.put("trackList",innerTrackList);
            trackList.add(trackMap);
        }
        result.put("trackList",trackList);
        return result;
    }

}
