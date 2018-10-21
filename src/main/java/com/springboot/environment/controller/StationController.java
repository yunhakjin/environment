package com.springboot.environment.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Station;
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

import javax.annotation.Resource;
import java.util.List;

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





}
