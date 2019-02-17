package com.springboot.environment.controller;

import com.springboot.environment.bean.Station;
import com.springboot.environment.bean.User;
import com.springboot.environment.service.GatherService;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.ConvertToJsonUtil;
import com.springboot.environment.util.StationConstant;
import com.springboot.environment.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Api("站点信息类相关api")
@RequestMapping(value = "/station")
public class StationController {


    @Autowired
    StationService stationService;

    @Autowired
    GatherService gatherService;


    @ApiOperation(value="查询所有站点信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/findAllStations", method = RequestMethod.GET)
    @ResponseBody
    public String findAllStations(){
        List<Station> stations = stationService.findALl();
        System.out.println(stations.size());
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }



    @ApiOperation(value="根据国控状态查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCountryCon",value="站点的国控状态标识",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByCountryCon/{isCountryCon}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByCountryCon(@PathVariable String isCountryCon){

        if (!isCountryCon.equals(StationConstant.IS_COUNTRY_CON_STR) && !isCountryCon.equals(StationConstant.ISNOT_COUNTRY_CON_STR)){
            System.out.println("参数格式不正确");
            return null;
        }
        List<Station> stations = stationService.queryStationsByCountryCon(Integer.parseInt(isCountryCon));
        return ConvertToJsonUtil.stationListConvertToJson(stations);

    }


    @ApiOperation(value="根据市控状态查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCityCon",value="站点的市控状态标识",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByCityCon/{isCityCon}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByCityCon(@PathVariable String isCityCon){
        if (!isCityCon.equals(StationConstant.IS_CITY_CON_STR) && !isCityCon.equals(StationConstant.ISNOT_CITY_CON_STR)){
            System.out.println("参数格式不正确");
            return null;
        }

        List<Station> stations = stationService.queryStationsByCityCon(Integer.parseInt(isCityCon));
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }



    @ApiOperation(value="根据区控状态查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isDomainCon",value="站点的区控状态标识",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByDomainCon/{isDomainCon}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByDomainCon(@PathVariable String isDomainCon){
        if (!isDomainCon.equals(StationConstant.IS_DOMAIN_CON_STR) && !isDomainCon.equals(StationConstant.ISNOT_DOMAIN_CON_STR)){
            System.out.println("参数格式错误");
            return null;
        }

        List<Station> stations = stationService.queryStationsByDomainCon(Integer.parseInt(isDomainCon));
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }



    @ApiOperation(value="根据站点是否运营查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationStatus",value="站点运营状态",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByStationStatus/{stationStatus}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByStationStatus(@PathVariable String stationStatus) {
        if (!stationStatus.equals(StationConstant.RUN_STR) && !stationStatus.equals(StationConstant.DISABLE_STR)) {
            System.out.println("参数格式不正确");
            return null;
        }

        List<Station> stations = stationService.queryStationsByStatus(Integer.parseInt(stationStatus));
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }



    @ApiOperation(value="根据站点是否在线查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "onlineFlag",value="站点在线状态",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByOnlineFlag/{onlineFlag}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByOnlineFlag(@PathVariable String onlineFlag){

        if (!onlineFlag.equals(StationConstant.ONLINE_STR) && !onlineFlag.equals(StationConstant.OFFLINE_STR)){
            System.out.println("参数格式不正确");
            return null;
        }

        List<Station> stations = stationService.queryStationsByOnlineFlag(Integer.parseInt(onlineFlag));
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }


    @ApiOperation(value="根据站点行政区域查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "district",value="行政区域",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByDistrict/{district}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByDistrict(@PathVariable String district){

        List<Station> stations = stationService.queryStationsByDistrict(district);

        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }



    @ApiOperation(value="根据站点所属街道查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "street",value="所属街道",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByStreet/{street}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByStreet(@PathVariable String street){

        List<Station> stations = stationService.queryStationsByStreet(street);
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }


    @ApiOperation(value="根据站点名模糊查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationName",value="站点名",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByStationName/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByName(@PathVariable String stationName){

        List<Station> stations = stationService.queryStationsByNameLike(stationName);

        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }


    @ApiOperation(value="根据站点区域环境信息查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area",value="区域环境枚举",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByArea/{area}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByArea(@PathVariable String area){

        if (!StringUtil.isInteger(area)){
            System.out.println("传入参数不是整形类型");
            return null;
        }

        List<Station> stations = stationService.queryStationsByArea(Integer.parseInt(area));
        return ConvertToJsonUtil.stationListConvertToJson(stations);
    }


    @ApiOperation(value="根据站点所属功能区查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "domain",value="所属功能区枚举",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByDomain/{domain}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByDomain(@PathVariable String domain){

        if (!StringUtil.isInteger(domain)){
            System.out.println("传入参数不是整形类型");
            return null;
        }

        List<Station> stations = stationService.queryStationsByDomain(Integer.parseInt(domain));
        return ConvertToJsonUtil.stationListConvertToJson(stations);

    }

    @ApiOperation(value = "查询所有的功能区",notes = "查询所有的功能区")
    @GetMapping(value = "/getfunc")
    public Map<String,Object> getFuncFromStation(){
        return stationService.getDomainFromStation();
    }

    @ApiOperation(value = "根据区域与功能区筛选站点",notes = "根据所选区域与功能区筛选站点")
    @ApiImplicitParam(name = "params",value="包含所选功能区与站点区域",dataType = "JSON")
    @RequestMapping(value = "/getStationsByAreasAndFuncCodes",method = RequestMethod.POST)
    public Map getStationsByAreasAndFuncCodes(@RequestBody Map<String,Object> params, HttpSession session){
        String operation_id = GetStationListByUser(session);
        //System.out.println("operation_id"+operation_id);
        return stationService.getStationsByAreasAndFuncCodes(params,operation_id);
    }

    @ApiOperation(value = "根据所属区和站点编号和站点名称模糊查询")
    @ApiImplicitParam(name = "params",value = "模糊查询的字符串",dataType = "String")
    @RequestMapping(value = "/getStationLike",method = RequestMethod.POST)
    public Map getStationsLike(@RequestBody Map<String,String> params,HttpSession session){
        Map<String,List> resultMap=new HashMap<String,List>();
        String operation_id = GetStationListByUser(session);
        String query=params.get("key");
        String area=params.get("area");
        if(area.equals("*")){
            List<Map> stationsList=new ArrayList<>();
            List<Station> stationList=stationService.queryStationsByNameLike(query);
            for(Station station:stationList){
                if(!operation_id.equals("0")){
                    if(station.getOperation_id().equals(operation_id)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station.getStationCode());
                        map.put("station_name",station.getStationName());
                        stationsList.add(map);
                    }
                }else{
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("station_id",station.getStationCode());
                    map.put("station_name",station.getStationName());
                    stationsList.add(map);
                }
            }
            resultMap.put("stations",stationsList);
        }else{
            List<Map> stationsList=new ArrayList<>();
            List<Station> stationList=stationService.queryStationsByNameLikeAndArea(area,query);
            System.out.println("stationList:"+stationList);
            for(Station station:stationList){
                if(!operation_id.equals("0")){
                    if(station.getOperation_id().equals(operation_id)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station.getStationCode());
                        map.put("station_name",station.getStationName());
                        stationsList.add(map);
                    }
                }else{

                        Map<String,String> map=new HashMap<String,String>();
                        map.put("station_id",station.getStationCode());
                        map.put("station_name",station.getStationName());
                        stationsList.add(map);

                }

            }
            resultMap.put("stations",stationsList);
        }
        return  resultMap;
    }

    @ApiOperation(value="根据key模糊查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value="模糊查询的key值,可能是id也可能是name",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByKey", method = RequestMethod.POST)
    public  String queryStationsByKey(@RequestBody Map<String, Object> params){

        System.out.println(params.toString());
        String key = params.get("key").toString();

        return stationService.queryStationsByKey(key);

    }

    @ApiOperation(value="返回所有街道")
    @RequestMapping(value = "/getAllStreet",method = RequestMethod.GET)
    public List<String> getAllStreet(){
        return stationService.getAllStreet();
    }

    @ApiOperation(value="增加某一站点")
    @ApiImplicitParam(name="params",value = "所要增加站点的属性",dataType = "JSON")
    @RequestMapping(value="/insertstation",method = RequestMethod.POST)
    public String insertStation (@RequestBody Map<String,String> params){
        String application = params.get("application");
        String area = params.get("area");
        String cityCon = params.get("cityCon");
        String countryCon = params.get("countryCon");
        String district = params.get("district");
        String domain = params.get("domain");
        String domainCon = params.get("domainCon");
        String station_code = params.get("stationCode");
        String station_id = params.get("stationId");
        String station_id_dz = params.get("stationIdDz");
        String station_name = params.get("stationName");
        String station_status = params.get("stationStatus");
        String online_flag = params.get("onlineFlag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocolName");
        String street = params.get("street");
        String station_major = params.get("stationMajor");
        String station_setup = params.get("stationSetup");
        String station_setupdate = params.get("stationSetupDate");
        String company_code = params.get("companyCode");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String station_position=params.get("stationPosition");
        String station_range=params.get("stationRange");
        String station_attribute=params.get("stationAttribute");
        String station_sim=params.get("stationSim");
        String operation_id=params.get("operationId");
        Station station = new Station();
        station.setApplication(application);
        station.setArea(Integer.valueOf(area));
        station.setCityCon(Integer.valueOf(cityCon));
        station.setCountryCon(Integer.valueOf(countryCon));
        station.setDistrict(district);
        station.setDomain(Integer.valueOf(domain));
        station.setDomainCon(Integer.valueOf(domainCon));
        station.setStationCode(station_code);
        station.setStationId(station_id);
        station.setStationIdDZ(station_id_dz);
        station.setStationName(station_name);
        station.setStationStatus(Integer.valueOf(station_status));
        station.setOnlineFlag(Integer.valueOf(online_flag));
        station.setProtocol(Integer.valueOf(protocol));
        station.setProtocolName(protocol_name);
        station.setStreet(street);
        station.setStation_major(station_major);
        station.setStation_setup(station_setup);
        station.setCompany_code(company_code);
        station.setClimate(Integer.valueOf(climate));
        station.setRadar(Integer.valueOf(radar));
        station.setPosition(station_position);
        station.setRange(station_range);
        station.setStation_attribute(Integer.valueOf(station_attribute));
        station.setStationSim(station_sim);
        station.setOperation_id(operation_id);
        if(stationService.getByStationId(station_code)!=null){
            return "已经存在此站点";
        }
        if(station_setupdate==""||station_setupdate==null)
            station_setupdate="1000-01-01 00:00:00";
        stationService.insertStation(station,station_setupdate);
        return "success";
    }

    @ApiOperation(value="删除某一站点")
    @ApiImplicitParam(name = "params",value = "所要删除站点的id",dataType = "JSON")
    @RequestMapping(value = "deletestation",method =RequestMethod.POST)
    public String deletestation(@RequestBody Map<String,String> parmas){
        String station_id=parmas.get("station_id");
        System.out.println(station_id);
        stationService.deleteStation(station_id);
        return "success";
    }

    @ApiOperation(value="更新某站点")
    @ApiImplicitParam(name = "params",value = "所要更新的参数以及目标站点",dataType = "JSON")
    @RequestMapping(value = "/updatestation",method = RequestMethod.POST)
    public String updatestation(@RequestBody Map<String,String> params){
        String application = params.get("application");
        String area = params.get("area");
        String cityCon = params.get("cityCon");
        String countryCon = params.get("countryCon");
        String district = params.get("district");
        String domain = params.get("domain");
        String domainCon = params.get("domainCon");
        String station_code = params.get("stationCode");
        String station_id = params.get("stationId");
        String station_id_dz = params.get("stationIdDz");
        String station_name = params.get("stationName");
        String station_status = params.get("stationStatus");
        String online_flag = params.get("onlineFlag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocolName");
        String street = params.get("street");
        String station_major = params.get("stationMajor");
        String station_setup = params.get("stationSetup");
        String station_setupdate = params.get("stationSetupDate");
        String company_code = params.get("companyCode");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String station_position=params.get("stationPosition");
        String station_range=params.get("stationRange");
        String station_attribute=params.get("stationAttribute");
        String station_sim=params.get("stationSim");
        String operation_id=params.get("operationId");
        String target=params.get("target");
        Station station = new Station();
        station.setApplication(application);
        station.setArea(Integer.valueOf(area));
        station.setCityCon(Integer.valueOf(cityCon));
        station.setCountryCon(Integer.valueOf(countryCon));
        station.setDistrict(district);
        station.setDomain(Integer.valueOf(domain));
        station.setDomainCon(Integer.valueOf(domainCon));
        station.setStationCode(station_code);
        station.setStationId(station_id);
        station.setStationIdDZ(station_id_dz);
        station.setStationName(station_name);
        station.setStationStatus(Integer.valueOf(station_status));
        station.setOnlineFlag(Integer.valueOf(online_flag));
        station.setProtocol(Integer.valueOf(protocol));
        station.setProtocolName(protocol_name);
        station.setStreet(street);
        station.setStation_major(station_major);
        station.setStation_setup(station_setup);
        station.setCompany_code(company_code);
        station.setClimate(Integer.valueOf(climate));
        station.setRadar(Integer.valueOf(radar));
        station.setPosition(station_position);
        station.setRange(station_range);
        station.setStation_attribute(Integer.valueOf(station_attribute));
        station.setStationSim(station_sim);
        station.setOperation_id(operation_id);
        if(stationService.getByStationId(target)==null){
            return "不存在此站点";
        }
        if(station_setupdate==""||station_setupdate==null)
            station_setupdate="1000-01-01 00:00:00";
        stationService.updateStation(station,station_setupdate,target);
        return "success";
    }

    @ApiOperation(value="查询某一站点信息")
    @ApiImplicitParam(name = "params")
    @RequestMapping(value = "/findoneStation", method = RequestMethod.POST)
    public Station findOneStation(@RequestBody Map<String,String> params){
        String station_id=params.get("id");
        return stationService.getByStationId(station_id);
    }

    @ApiOperation(value="GEOJSON 返回接口信息")
    @ApiImplicitParam(name = "params")
    @RequestMapping(value = "/GEOJson", method = RequestMethod.POST)
    public Map GEOJson(@RequestBody Map<String,String> params,HttpSession session){
        String operation_id = GetStationListByUser(session);
        return stationService.GEOJson(params,operation_id);
    }

    @ApiOperation(value="通过用户id获取stationList")
    @ApiImplicitParam(name = "params")
    @RequestMapping(value = "/GetStationListByUser", method = RequestMethod.GET)
    public String GetStationListByUser(HttpSession session){
        User user=(User) session.getAttribute("user");
        System.out.println("userOnline"+user);
        String operatationId=user.getOperation_id();
        if(operatationId==null||operatationId.equals("")||operatationId.equals("null")){
            System.out.println("超级管理员或无运维单位人员");
            operatationId="0";
        }
        //System.out.println(operatationId);
        return operatationId;
    }

    @ApiOperation(value = "根据行政区和功能区查询")
    @ApiImplicitParam(name = "params",value = "行政区，功能区",dataType = "JSON")
    @RequestMapping(value = "/getStationsByDistrictAndDomain",method = RequestMethod.POST)
    public Map getStationsByDistrictAndDomain(@RequestBody Map<String,Object> params){
        String district = params.get("district_name").toString();
        int domain = (Integer) params.get("domain_id");
        Set<Map> stationSet=new HashSet<>();
        List<Station> stationList=stationService.queryStationsByDistrictAndDomain(district,domain);
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            map.put("station_id",station.getStationCode());
            map.put("station_name",station.getStationName());
            stationSet.add(map);
        }
        List<Map> resultList=new ArrayList<Map>();
        resultList.addAll(stationSet);
        Map<String,List> resultMap=new HashMap<String,List>();
        resultMap.put("stations",resultList);
        return  resultMap;
    }


}
