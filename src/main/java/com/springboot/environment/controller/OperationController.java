package com.springboot.environment.controller;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.springboot.environment.bean.Gather;
import com.springboot.environment.bean.Operation;
import com.springboot.environment.bean.Station;
import com.springboot.environment.bean.User;
import com.springboot.environment.service.GatherService;
import com.springboot.environment.service.OperationService;
import com.springboot.environment.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/operation")
@Api(value = "维护单位类api")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @Autowired
    private StationService stationService;

    @Autowired
    private GatherService gatherService;

    @ApiOperation(value = "查询所有的运维单位")
    @RequestMapping(value = "/getalloperation",method = RequestMethod.GET)
    public List<Operation> getAllOperation(){
        return operationService.getAllOperation();
    }

    @ApiOperation(value="增加运维单位")
    @ApiImplicitParam(value = "新增运维单位的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/addoperation",method = RequestMethod.POST)
    public String addOperation(@RequestBody Map<String,Object> params){
        String operation_id=(String)params.get("operationId");
        String operation_name=(String)params.get("operationName");
        String operation_relate=(String)params.get("operationRelate");
        String operation_tel=(String)params.get("operationTel");
        String operation_target=(String)params.get("operationTarget");
        String target=(String)params.get("target");
        if(!operationService.getOneOperation(operation_id).isEmpty()){
            return "已存在此运维单位";
        }
        Operation operation=new Operation();
        operation.setOperation_id(operation_id);
        operation.setOperation_name(operation_name);
        operation.setOperation_relate(operation_relate);
        operation.setOperation_tel(operation_tel);
        operation.setOperation_target(operation_target);
        operationService.insertOperation(operation);
        List<String> station_set=(List)params.get("stationSet");
        List<String> station_unset=(List)params.get("stationUnset");
        List<String> gather_set=(List)params.get("gatherSet");
        List<String> gather_unset=(List)params.get("gatherUnset");
        for(String station:station_set){
            stationService.updateStationOperation(operation_id,station);
        }
        for(String gather:gather_set){
            gatherService.updateGatherOperation(operation_id,gather);
        }
        return "success";
    }

    @ApiOperation(value="删除运维单位")
    @ApiImplicitParam(value = "删除运维单位的json",name = "params",dataType = "JSON")
    @RequestMapping(value="/deleteoperation",method = RequestMethod.POST)
    public String delOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        operationService.deleteOperation(operation_id);
        List<Station> stationList=stationService.findALl();
        for(Station station:stationList){
            if(station.getOperation_id().equals(operation_id)){
                stationService.updateStationOperation("",station.getStationCode());
            }
        }
        List<Gather> gatherList=gatherService.getAllGather();
        for(Gather gather:gatherList){
            if(gather.getOperation_id().equals(operation_id)){
                gatherService.updateGatherOperation("",gather.getGather_id());
            }
        }
        return "success";
    }

    @ApiOperation(value="修改运维单位")
    @ApiImplicitParam(value="修改运维单位的json",name = "params",dataType = "JSON")
    @RequestMapping(value = "/updateoperation",method = RequestMethod.POST)
    public String updateOperation(@RequestBody Map<String,Object> params){
        String operation_id=(String)params.get("operationId");
        String operation_name=(String)params.get("operationName");
        String operation_relate=(String)params.get("operationRelate");
        String operation_tel=(String)params.get("operationTel");
        String operation_target=(String)params.get("operationTarget");
        String target=(String)params.get("target");
        Operation operation=new Operation();
        operation.setOperation_id(operation_id);
        operation.setOperation_name(operation_name);
        operation.setOperation_relate(operation_relate);
        operation.setOperation_tel(operation_tel);
        operation.setOperation_target(operation_target);
        operationService.updateOperation(operation,target);

        List<String> station_set=(List)params.get("stationSet");
        List<String> station_unset=(List)params.get("stationUnset");
        List<String> gather_set=(List)params.get("gatherSet");
        List<String> gather_unset=(List)params.get("gatherUnset");
        for(String station:station_set){
            stationService.updateStationOperation(operation_id,station);
        }
        for(String station:station_unset){
            stationService.updateStationOperation("",station);
        }
        for(String gather:gather_set){
            gatherService.updateGatherOperation(operation_id,gather);
        }
        for(String gather:gather_unset){
            gatherService.updateGatherOperation("",gather);
        }
        return "success";
    }

    @ApiOperation(value = "运维单位的模糊搜索")
    @ApiImplicitParam(value = "运维单位模糊查询的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/getoperationlike",method = RequestMethod.POST)
    public List<Operation> getOperationLike(@RequestBody Map<String,String> params){
        String target=params.get("target");
        return operationService.getOperationLike(target);
    }

    @ApiOperation(value="返回目标运维单位管辖的站点")
    @ApiImplicitParam(value = "返回目标运维单位的code的JSON",name = "params",dataType = "JSON")
    @RequestMapping(value = "/getstationoperation",method = RequestMethod.POST)
    public List<Map> getStationOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        List<Map> result=new ArrayList<Map>();
        List<Station> stationList=stationService.findALl();
        List<Gather> gatherList=gatherService.getAllGather();
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            if(station.getOperation_id().equals(operation_id)){
                map.put("stationCode",station.getStationCode());
                map.put("stationSim",station.getStationSim());
                map.put("stationName",station.getStationName());
                map.put("flag","1");
                result.add(map);
            }
            else if(station.getOperation_id().equals("")){
                map.put("stationCode",station.getStationCode());
                map.put("stationSim",station.getStationSim());
                map.put("stationName",station.getStationName());
                map.put("flag","0");
                result.add(map);
            }
        }
        return result;
    }

    @ApiOperation(value="返回目标运维单位管辖的采集车")
    @ApiImplicitParam(value = "返回目标运维单位的code的JSON",name = "params",dataType = "JSON")
    @RequestMapping(value = "/getgatheroperation",method = RequestMethod.POST)
    public List<Map> getGatherOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        List<Map> result=new ArrayList<Map>();
        List<Gather> gatherList=gatherService.getAllGather();
        for(Gather gather:gatherList){
            Map<String,String> map=new HashMap<String,String>();
            if(gather.getOperation_id().equals(operation_id)){
                map.put("gatherId",gather.getGather_id());
                map.put("gatherCode",gather.getGather_code());
                map.put("gatherName",gather.getGather_name());
                map.put("flag","1");
                map.put("type","gather");
                result.add(map);
            }
            else if(gather.getOperation_id().equals("")){
                map.put("gatherId",gather.getGather_id());
                map.put("gatherCode",gather.getGather_code());
                map.put("gatherName",gather.getGather_name());
                map.put("flag","0");
                map.put("type","gather");
                result.add(map);
            }
        }
        return result;
    }

}
