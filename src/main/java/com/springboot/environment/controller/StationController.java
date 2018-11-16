package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Station;
import com.springboot.environment.request.QueryStationsByKeyReq;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.ConvertToJsonUtil;
import com.springboot.environment.util.StationConstant;
import com.springboot.environment.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.annotation.Resource;
import java.util.*;

@RestController
@Api("站点信息类相关api")
@RequestMapping(value = "/station")
public class StationController {


    @Autowired
    StationService stationService;



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




    @ApiOperation(value="新增站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationId",value="对内站点编号",dataType = "String"),
            @ApiImplicitParam(name = "stationCode",value="对外站点编号",dataType = "String"),
            @ApiImplicitParam(name = "stationName",value="站点名称",dataType = "String"),
            @ApiImplicitParam(name = "stationStatus",value="站点状态",dataType = "String"),
            @ApiImplicitParam(name = "application",value="所属应用(暂未使用)",dataType = "String"),
            @ApiImplicitParam(name = "onlineFlag",value="在线标识",dataType = "String"),
            @ApiImplicitParam(name = "stationIdDZ",value="对照码",dataType = "String"),
            @ApiImplicitParam(name = "protocol",value="所用协议",dataType = "String"),
            @ApiImplicitParam(name = "protocolName",value="所用协议名称",dataType = "String"),
            @ApiImplicitParam(name = "position",value="站点位置",dataType = "String"),
            @ApiImplicitParam(name = "street",value="站点所属街道",dataType = "String"),
            @ApiImplicitParam(name = "district",value="站点所属行政区",dataType = "String"),
            @ApiImplicitParam(name = "range",value="噪声点范围",dataType = "String"),
            @ApiImplicitParam(name = "countryCon",value="国控",dataType = "String"),
            @ApiImplicitParam(name = "cityCon",value="市控",dataType = "String"),
            @ApiImplicitParam(name = "domainCon",value="区控",dataType = "String"),
            @ApiImplicitParam(name = "area",value="区域环境",dataType = "String"),
            @ApiImplicitParam(name = "domain",value="功能区",dataType = "String")
    })
    @RequestMapping(value = "/addStation/{stationId}/{stationCode}/{stationName}/{stationStatus}/{application}/{onlineFlag}/{stationIdDZ}/{protocol}/{protocolName}/{position}/{street}/{district}/{range}/{countryCon}/{cityCon}/{domainCon}/{area}/{domain}", method = RequestMethod.GET)
    @ResponseBody
    public String addStation(@PathVariable String stationId,
                             @PathVariable String stationCode,
                             @PathVariable String stationName,
                             @PathVariable String stationStatus,
                             @PathVariable String application,
                             @PathVariable String onlineFlag,
                             @PathVariable String stationIdDZ,
                             @PathVariable String protocol,
                             @PathVariable String protocolName,
                             @PathVariable String position,
                             @PathVariable String street,
                             @PathVariable String district,
                             @PathVariable String range,
                             @PathVariable String countryCon,
                             @PathVariable String cityCon,
                             @PathVariable String domainCon,
                             @PathVariable String area,
                             @PathVariable String domain){

        return stationService.addStation(stationId, stationCode, stationName, stationStatus, application, onlineFlag, stationIdDZ, protocol, protocolName, position, street, district, range, countryCon, cityCon, domainCon, area, domain);

    }


    @ApiOperation(value="根据站点Id删除站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationId",value="站点对内Id",dataType = "String")
    })

    @RequestMapping(value = "/deleteByStationId/{stationId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteByStationId(@PathVariable String stationId){

        return stationService.deleteStationByStationId(stationId);
    }

    @ApiOperation(value = "查询所有的功能区",notes = "查询所有的功能区")
    @GetMapping(value = "/getfunc")
    public Map<String,Object> getFuncFromStation(){
        return stationService.getDomainFromStation();
    }

    @ApiOperation(value = "根据区域与功能区筛选站点",notes = "根据所选区域与功能区筛选站点")
    @ApiImplicitParam(name = "params",value="包含所选功能区与站点区域",dataType = "JSON")
    @RequestMapping(value = "/getStationsByAreasAndFuncCodes",method = RequestMethod.POST)
    public Map<String,Object> getStationsByAreasAndFuncCodes(@RequestBody Map<String,Object> params){
        return stationService.getStationsByAreasAndFuncCodes(params);
    }

    @ApiOperation(value = "根据站点编号和站点名称模糊查询")
    @ApiImplicitParam(name = "params",value = "模糊查询的字符串",dataType = "String")
    @RequestMapping(value = "/getStationLike",method = RequestMethod.POST)
    public Map getStationsLike(@RequestBody Map<String,String> params){
        String query=params.get("key");
        Set<Map> stationSet=new HashSet<>();
        List<Station> stationList=stationService.queryStationsByNameLike(query);
        for(Station station:stationList){
            Map<String,String> map=new HashMap<String,String>();
            map.put("station_id",station.getStationCode());
            map.put("station_name",station.getStationName());
            stationSet.add(map);
        }
        stationList=stationService.queryStationsByCodeLike(query);
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
        String cityCon = params.get("city_con");
        String countryCon = params.get("country_con");
        String district = params.get("district");
        String domain = params.get("domain");
        String domainCon = params.get("domain_con");
        String station_code = params.get("station_code");
        String station_id = params.get("station_id");
        String station_id_dz = params.get("station_id_dz");
        String station_name = params.get("station_name");
        String station_status = params.get("station_status");
        String online_flag = params.get("online_flag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocol_name");
        String street = params.get("street");
        String station_major = params.get("station_major");
        String station_setup = params.get("station_setup");
        String station_setupdate = params.get("station_setupdate");
        String company_code = params.get("company_code");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String station_position=params.get("station_position");
        String station_range=params.get("station_range");
        String station_attribute=params.get("station_attribute");
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
        if(stationService.getByStationId(station_id)!=null){
            return "已经存在此采集车";
        }
        stationService.insertStation(station,station_setupdate);
        return "success";
    }

    @ApiOperation(value="删除某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要删除采集车的id",dataType = "JSON")
    @RequestMapping(value = "deletestation",method =RequestMethod.DELETE)
    public String deletestation(@RequestBody Map<String,String> parmas){
        String station_id=parmas.get("station_id");
        stationService.deleteStation(station_id);
        return "success";
    }

    @ApiOperation(value="更新某一辆采集车")
    @ApiImplicitParam(name = "params",value = "所要更新的参数以及目标车辆",dataType = "JSON")
    @RequestMapping(value = "/updatestation",method = RequestMethod.POST)
    public String updatestation(@RequestBody Map<String,String> params){
        String application = params.get("application");
        String area = params.get("area");
        String cityCon = params.get("city_con");
        String countryCon = params.get("country_con");
        String district = params.get("district");
        String domain = params.get("domain");
        String domainCon = params.get("domain_con");
        String station_code = params.get("station_code");
        String station_id = params.get("station_id");
        String station_id_dz = params.get("station_id_dz");
        String station_name = params.get("station_name");
        String station_status = params.get("station_status");
        String online_flag = params.get("online_flag");
        String protocol = params.get("protocol");
        String protocol_name = params.get("protocol_name");
        String street = params.get("street");
        String station_major = params.get("station_major");
        String station_setup = params.get("station_setup");
        String station_setupdate = params.get("station_setupdate");
        String company_code = params.get("company_code");
        String climate = params.get("climate");
        String radar = params.get("radar");
        String target=params.get("target");
        String station_position=params.get("station_position");
        String station_range=params.get("station_range");
        String station_attribute=params.get("station_attribute");
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
        if(stationService.getByStationId(target)==null){
            return "不存在采集车";
        }
        stationService.updateStation(station,station_setupdate,target);
        return "success";
    }

}
