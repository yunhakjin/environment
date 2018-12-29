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

    @ApiOperation(value="查询单个运维单位")
    @ApiImplicitParam(value="查询某一个运维站点Id的JSON",name = "params",dataType = "JSON")
    @RequestMapping(value = "/getoneoperation",method = RequestMethod.POST)
    public Map getOneOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        List<Operation> operationList=operationService.getOneOperation(operation_id);
        Operation operation=new Operation();
        for(Operation operation1:operationList){
            operation=operation1;
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("operation_id",operation.getOperation_id());
        map.put("operation_name",operation.getOperation_name());
        map.put("operation_relate",operation.getOperation_relate());
        map.put("operation_tel",operation.getOperation_tel());
        List<Map> station_set=new ArrayList<Map>();
        List<Map> station_unset=new ArrayList<Map>();
        List<Map> gatherSet=new ArrayList<Map>();
        List<Station> stationList=stationService.findALl();
        List<Gather> gatherList=gatherService.getAllGather();
        for(Station station:stationList){
            Map<String,String> innermap=new HashMap<String,String>();
            if(!station.getOperation_id().equals("")&&station.getOperation_id().equals(operation_id)){
                innermap.put("stationCode",station.getStationCode());
                innermap.put("stationSim",station.getStationSim());
                innermap.put("stationName",station.getStationName());
                innermap.put("stationDistrict",station.getDistrict());
                station_set.add(innermap);
            }
            else if(station.getOperation_id().equals("")){
                innermap.put("stationCode",station.getStationCode());
                innermap.put("stationSim",station.getStationSim());
                innermap.put("stationName",station.getStationName());
                innermap.put("stationDistrict",station.getDistrict());
                station_unset.add(innermap);
            }
        }
        for(Gather gather:gatherList){
            Map<String,String> innermap=new HashMap<String,String>();
            if(!gather.getOperation_id().equals("")&&gather.getOperation_id()!=null&&gather.getOperation_id().equals(operation_id)){
                innermap.put("gatherId",gather.getGather_id());
                innermap.put("gatherCode",gather.getGather_code());
                innermap.put("gatherName",gather.getGather_name());
                innermap.put("flag","true");
                gatherSet.add(innermap);
            }
            else if(gather.getOperation_id().equals("")||gather.getOperation_id()==null){
                innermap.put("gatherId",gather.getGather_id());
                innermap.put("gatherCode",gather.getGather_code());
                innermap.put("gatherName",gather.getGather_name());
                innermap.put("flag","false");
                gatherSet.add(innermap);
            }
        }
        map.put("station_set",station_set);
        map.put("station_unset",station_unset);
        map.put("gather",gatherSet);
        return map;
    }

    @ApiOperation(value="增加运维单位")
    @ApiImplicitParam(value = "新增运维单位的json",name = "params",dataType = "JSON")
    @RequestMapping(value = "/addoperation",method = RequestMethod.POST)
    public String addOperation(@RequestBody Map<String,Object> params){
        String operation_id=(String)params.get("operationId");
        String operation_name=(String)params.get("operationName");
        String operation_relate=(String)params.get("operationRelate");
        String operation_tel=(String)params.get("operationTel");
        if(!operationService.getOneOperation(operation_id).isEmpty()){
            return "已存在此运维单位";
        }
        Operation operation=new Operation();
        operation.setOperation_id(operation_id);
        operation.setOperation_name(operation_name);
        operation.setOperation_relate(operation_relate);
        operation.setOperation_tel(operation_tel);
        operationService.insertOperation(operation);
        List<String> station_set=(List)params.get("stationSet");
        List<Map> gather_set=(List)params.get("gather");
        for(String station:station_set){
            stationService.updateStationOperation(operation_id,station);
        }
        for(Map map:gather_set){
            if(map.get("flag").equals("true")){
            gatherService.updateGatherOperation(operation_id,(String)map.get("gatherId"));}
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
        String target=(String)params.get("target");
        Operation operation=new Operation();
        operation.setOperation_id(operation_id);
        operation.setOperation_name(operation_name);
        operation.setOperation_relate(operation_relate);
        operation.setOperation_tel(operation_tel);
        operationService.updateOperation(operation,target);

        List<String> station_set=(List)params.get("stationSet");
        List<String> station_unset=(List)params.get("stationUnset");
        List<Map> gatherList=(List)params.get("gather");
        List<String> gather_set=new ArrayList<String>();
        List<String> gather_unset=new ArrayList<String>();
        for(Map map:gatherList){
            String gather_id=(String)map.get("gatherId");
            String flag=(String)map.get("flag");
            if(flag.equals("true")){
                gather_set.add(gather_id);
            }
            else{
                gather_unset.add(gather_id);
            }
        }
        System.out.println(station_set);
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

    @ApiOperation(value="站点模糊查询")
    @ApiImplicitParam(value = "运维单位id的JSON",name = "params",dataType = "JSON")
    @RequestMapping(value = "getstation",method=RequestMethod.POST)
    public List<Station> getStation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        String key=params.get("key");
        String district=params.get("area");
        if(district.equals("*")){
            return stationService.getOperationStationLikeAll(operation_id,key);
        }
        else {
            return stationService.getOperationStationLike(district, operation_id, key);
        }
    }


}
