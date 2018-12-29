package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Gather;
import com.springboot.environment.bean.GatherData;
import com.springboot.environment.bean.User;
import com.springboot.environment.service.GatherDataService;
import com.springboot.environment.service.GatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @ApiOperation(value = "返回所有的采集车信息")
    @RequestMapping(value = "/getallgatherInfo",method = RequestMethod.GET)
    public List getAllGatherInfo(HttpSession httpSession){
        List<Gather> gatherList=new ArrayList<Gather>();
        User user=(User)httpSession.getAttribute("user");
        String operation_id=user.getOperation_id();
        if(operation_id==null||operation_id.equals("")){
            gatherList=gatherService.getAllGather();
        }
        else{
            gatherList=gatherService.getGatherByOperation_id(operation_id);
        }
        List<Map> result=new ArrayList<Map>();
        for(Gather gather:gatherList){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("gather_id",gather.getGather_id());
            map.put("gather_code",gather.getGather_code());
            map.put("gather_name",gather.getGather_name());
            map.put("gather_status",gather.getGather_status());
            map.put("application",gather.getApplication());
            map.put("online_flag",gather.getOnline_flag());
            map.put("gather_id_dz",gather.getGather_id_dz());
            map.put("protocol",gather.getProtocol());
            map.put("protocol_name",gather.getProtocol_name());
            map.put("street",gather.getStreet());
            map.put("district",gather.getDistrict());
            map.put("country_con",gather.getCountry_con());
            map.put("city_con",gather.getCity_con());
            map.put("domain_con",gather.getDomain_con());
            map.put("area",gather.getDomain());
            map.put("domain",gather.getDomain());
            map.put("gather_major",gather.getGather_major());
            map.put("gather_setup",gather.getGather_setup());
            map.put("gather_setupdate",gather.getGather_setupdate().toString());
            map.put("company_code",gather.getCompany_code());
            map.put("climate",gather.getClimate());
            map.put("radar",gather.getRadar());
            map.put("operation_id",gather.getOperation_id());
            result.add(map);
        }
        return result;
    }

//    @ApiOperation(value = "根据采集车id返回其所有轨迹",notes = "根据采集车id返回其所有轨迹")
//    @ApiImplicitParam(name = "params",value="包含采集车id列表和查询时间的json",dataType = "JSON")
//    @RequestMapping(value = "/getgatherdata",method = RequestMethod.POST)
//    public Map getGatherDataByGatherId(@RequestBody Map<String,Object> params){
//        String json="{query:{date:"2018-09-07",cars:["movingcar06","movingcar05"]}}";
//        Map gatherQuery=JSONObject.parseObject(query);
//        Map gatherQuery=(Map)params.get("query");
//        String date=(String)gatherQuery.get("date");
//        List<String> cars=(List)gatherQuery.get("cars");
//        Map<String,List> result=new HashMap<String, List>();
//        List<Map> trackList=new ArrayList<Map>();
//        for(int i=0;i<cars.size();i++){
//            Map<String,Object> trackMap=new HashMap<String,Object>();
//            String car=cars.get(i);
//            trackMap.put("carName",car);
//            List<GatherData> gatherDataList=gatherDataService.getAllByGather_idAndData_time(car,date);
//            Map<String,Map> innertrackMap=new HashMap<String,Map>();
//            for(int j=0;j<gatherDataList.size();j++){
//                String trackTime=gatherDataList.get(j).getData_time().toString();
//                Map<String,Object> normVal=new HashMap<String,Object>();
//                if(innertrackMap.containsKey(trackTime)){
//                    normVal.put(gatherDataList.get(j).getNorm_code(),gatherDataList.get(j).getNorm_val());
//                    innertrackMap.get(trackTime).putAll(normVal);
//                }
//                else{
//                    normVal.put("time",trackTime);
//                    List<String> position= Arrays.asList(gatherDataList.get(j).getGather_position().replace('(',' ').replace(')',' ').split(","));
//                    List<Double> pos=new ArrayList<Double>();
//                    if(!position.isEmpty()){
//                        pos.add(Double.parseDouble(position.get(0)));
//                        pos.add(Double.parseDouble(position.get(1)));
//                    }
//                    normVal.put("position",pos);
//                    normVal.put(gatherDataList.get(j).getNorm_code(),gatherDataList.get(j).getNorm_val());
//                    innertrackMap.put(trackTime,normVal);
//                }
//            }
//            innertrackMap=innertrackMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
//                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newVvalue)->oldValue,LinkedHashMap::new));
//
//            List<Map> innerTrackList=new ArrayList<Map>();
//            for(Map value:innertrackMap.values()){
//                innerTrackList.add(value);
//            }
//            trackMap.put("trackList",innerTrackList);
//            trackList.add(trackMap);
//        }
//
//        result.put("trackList",trackList);
//        return result;
//    }

    @ApiOperation(value = "根据采集车id返回其所有轨迹",notes = "根据采集车id返回其所有轨迹")
    @ApiImplicitParam(name = "params",value="包含采集车id列表和查询时间的json",dataType = "JSON")
    @RequestMapping(value = "/getgatherdata",method = RequestMethod.POST)
    public Map getGatherDataByGatherId(@RequestBody Map<String,Object> params){
        //{"query":{"date":"2018-09-07","cars":["movingcar06"]}}
        Map gatherQuery=(Map)params.get("query");
        String date=(String)gatherQuery.get("date");
        List<String> cars=(List)gatherQuery.get("cars");
        Map<String,Object> result=new HashMap<String,Object>();
        Map<String,Object> trackMap=new HashMap<String,Object>();
        for(int i=0;i<cars.size();i++){
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
                    normVal.put("type","Feature");
                    normVal.put("id",gatherDataList.get(j).getData_id());
                    Map<String,Object> geometry=new HashMap<String,Object>();
                    normVal.put("time",trackTime);
                    List<String> position= Arrays.asList(gatherDataList.get(j).getGather_position().replace('(',' ').replace(')',' ').split(","));
                    List<Double> pos=new ArrayList<Double>();
                    if(!position.isEmpty()){
                        pos.add(Double.parseDouble(position.get(0)));
                        pos.add(Double.parseDouble(position.get(1)));
                    }
                    geometry.put("type","Point");
                    geometry.put("coordinates",pos);
                    normVal.put("geometry",geometry);
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
        }
        result.put("type","FeatureCollection");
        result.put("features",trackMap.get("trackList"));
        return result;
    }

    @ApiOperation(value="增加某一辆采集车")
    @ApiImplicitParam(name="params",value = "所要增加采集车的属性",dataType = "JSON")
    @RequestMapping(value="/insertgather",method = RequestMethod.POST)
    public String insertGather (@RequestBody Map<String,String> params){
           String application = params.get("application");
           String area = params.get("area");
           String city_con = params.get("cityCon");
           String country_con = params.get("countryCon");
           String district = params.get("district");
           String domain = params.get("domain");
           String domain_con = params.get("domainCon");
           String gather_code = params.get("gatherCode");
           String gather_id = params.get("gatherId");
           String gather_id_dz = params.get("gatherIdDz");
           String gather_name = params.get("gatherName");
           String gather_status = params.get("gatherStatus");
           String online_flag = params.get("onlineFlag");
           String protocol = params.get("protocol");
           String protocol_name = params.get("protocolName");
           String street = params.get("street");
           String gather_major = params.get("gatherMajor");
           String gather_setup = params.get("gatherSetup");
           String gather_setupdate = params.get("gatherSetupDate");
           String company_code = params.get("companyCode");
           String climate = params.get("climate");
           String radar = params.get("radar");
           String operation_id=params.get("operation_id");
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
           gather.setOperation_id(operation_id);
           if(gatherService.getOneGather(gather_id)!=null){
               return "已经存在此采集车";
           }
           gatherService.insertGather(gather,gather_setupdate);
           return "success";
    }

    @ApiOperation(value="删除某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要删除采集车的id",dataType = "JSON")
    @RequestMapping(value = "deletegather",method =RequestMethod.POST)
    public String deleteGather(@RequestBody Map<String,String> parmas){
        String gather_id=parmas.get("gatherId");
        gatherService.deleteGather(gather_id);
        return "success";
    }

    @ApiOperation(value="更新某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要更新的参数以及目标车辆",dataType = "JSON")
    @RequestMapping(value = "/updategather",method = RequestMethod.POST)
    public String updateGather(@RequestBody Map<String,String> params){
        String application = params.get("application");
        String area = params.get("area");
        String city_con = params.get("cityCon");
        String country_con = params.get("countryCon");
        String district = params.get("district");
        String domain = params.get("domain");
        String domain_con = params.get("domainCon");
        String gather_code = params.get("gatherCode");
        String gather_id = params.get("gatherId");
        String gather_id_dz = params.get("gatherIdDz");
        String gather_name = params.get("gatherName");
        String gather_status = params.get("gatherStatus");
        String online_flag = params.get("onlineFlag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocolName");
        String street = params.get("street");
        String gather_major = params.get("gatherMajor");
        String gather_setup = params.get("gatherSetup");
        String gather_setupdate = params.get("gatherSetupDate");
        String company_code = params.get("companyCode");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String operation_id=params.get("operation_id");
        String target=params.get("target");
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
        gather.setOperation_id(operation_id);
        if(gatherService.getOneGather(target)==null){
            return "不存在采集车";
        }
        gatherService.updateGather(gather,gather_setupdate,target);
        return "success";
    }
}
