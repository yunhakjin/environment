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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
                    List<Double> pos=new ArrayList<Double>();
                    if(!position.isEmpty()){
                        pos.add(Double.parseDouble(position.get(0)));
                        pos.add(Double.parseDouble(position.get(1)));
                    }
                    normVal.put("position",pos);
                    normVal.put(gatherDataList.get(j).getNorm_code(),gatherDataList.get(j).getNorm_val());
                    innertrackMap.put(trackTime,normVal);
                }
            }
            innertrackMap=innertrackMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newVvalue)->oldValue,LinkedHashMap::new));

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

    @ApiOperation(value="增加某一辆采集车")
    @ApiImplicitParam(name="params",value = "所要增加采集车的属性",dataType = "JSON")
    @RequestMapping(value="/insertgather",method = RequestMethod.POST)
    public String insertGather (@RequestBody Map<String,String> params){
           String application = params.get("application");
           String area = params.get("area");
           String city_con = params.get("city_con");
           String country_con = params.get("country_con");
           String district = params.get("district");
           String domain = params.get("domain");
           String domain_con = params.get("domain_con");
           String gather_code = params.get("gather_code");
           String gather_id = params.get("gather_id");
           String gather_id_dz = params.get("gather_id_dz");
           String gather_name = params.get("gather_name");
           String gather_status = params.get("gather_status");
           String online_flag = params.get("online_flag");
           String protocol = params.get("protocol");
           String protocol_name = params.get("protocol_name");
           String street = params.get("street");
           String gather_major = params.get("gather_major");
           String gather_setup = params.get("gather_setup");
           String gather_setupdate = params.get("gather_setupdate");
           String company_code = params.get("company_code");
           String climate = params.get("climate");
           String radar = params.get("radar");
           String d_limit=params.get("d_limit");
           String n_limit=params.get("n_limit");
           Gather gather = new Gather();
           gather.setApplication(application);
           gather.setArea(Integer.valueOf(area));
           gather.setCity_con(Integer.valueOf(city_con));
           gather.setCountry_con(Integer.valueOf(country_con));
           gather.setDistrict(district);
           gather.setDomain(Integer.valueOf(domain));
           gather.setDomain_con(Integer.valueOf(domain_con));
           gather.setGather_code(gather_code);
           gather.setGather_id(gather_id);
           gather.setGather_id_dz(gather_id_dz);
           gather.setGather_name(gather_name);
           gather.setGather_status(Integer.valueOf(gather_status));
           gather.setOnline_flag(Integer.valueOf(online_flag));
           gather.setProtocol(Integer.valueOf(protocol));
           gather.setProtocol_name(protocol_name);
           gather.setStreet(street);
           gather.setGather_major(gather_major);
           gather.setGather_setup(gather_setup);
           gather.setCompany_code(company_code);
           gather.setClimate(Integer.valueOf(climate));
           gather.setRadar(Integer.valueOf(radar));
           gather.setD_limit(d_limit);
           gather.setN_limit(n_limit);
           if(gatherService.getOneGather(gather_id)!=null){
               return "已经存在此采集车";
           }
           gatherService.insertGather(gather,gather_setupdate);
           return "success";
    }

    @ApiOperation(value="删除某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要删除采集车的id",dataType = "JSON")
    @RequestMapping(value = "deletegather",method =RequestMethod.DELETE)
    public String deleteGather(@RequestBody Map<String,String> parmas){
        String gather_id=parmas.get("gather_id");
        gatherService.deleteGather(gather_id);
        return "success";
    }

    @ApiOperation(value="更新某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要更新的参数以及目标车辆",dataType = "JSON")
    @RequestMapping(value = "/updategather",method = RequestMethod.POST)
    public String updateGather(@RequestBody Map<String,String> params){
        String application = params.get("application");
        String area = params.get("area");
        String city_con = params.get("city_con");
        String country_con = params.get("country_con");
        String district = params.get("district");
        String domain = params.get("domain");
        String domain_con = params.get("domain_con");
        String gather_code = params.get("gather_code");
        String gather_id = params.get("gather_id");
        String gather_id_dz = params.get("gather_id_dz");
        String gather_name = params.get("gather_name");
        String gather_status = params.get("gather_status");
        String online_flag = params.get("online_flag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocol_name");
        String street = params.get("street");
        String gather_major = params.get("gather_major");
        String gather_setup = params.get("gather_setup");
        String gather_setupdate = params.get("gather_setupdate");
        String company_code = params.get("company_code");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String target=params.get("target");
        String d_limit=params.get("d_limit");
        String n_limit=params.get("n_limit");
        Gather gather = new Gather();
        gather.setApplication(application);
        gather.setArea(Integer.valueOf(area));
        gather.setCity_con(Integer.valueOf(city_con));
        gather.setCountry_con(Integer.valueOf(country_con));
        gather.setDistrict(district);
        gather.setDomain(Integer.valueOf(domain));
        gather.setDomain_con(Integer.valueOf(domain_con));
        gather.setGather_code(gather_code);
        gather.setGather_id(gather_id);
        gather.setGather_id_dz(gather_id_dz);
        gather.setGather_name(gather_name);
        gather.setGather_status(Integer.valueOf(gather_status));
        gather.setOnline_flag(Integer.valueOf(online_flag));
        gather.setProtocol(Integer.valueOf(protocol));
        gather.setProtocol_name(protocol_name);
        gather.setStreet(street);
        gather.setGather_major(gather_major);
        gather.setGather_setup(gather_setup);
        gather.setCompany_code(company_code);
        gather.setClimate(Integer.valueOf(climate));
        gather.setRadar(Integer.valueOf(radar));
        gather.setD_limit(d_limit);
        gather.setN_limit(n_limit);
        if(gatherService.getOneGather(target)==null){
            return "不存在采集车";
        }
        gatherService.updateGather(gather,gather_setupdate,target);
        return "success";
    }
}
