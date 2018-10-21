package com.springboot.environment.controller;

import com.springboot.environment.bean.Station;
import com.springboot.environment.service.StationService;
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



    @ApiOperation(value="根据国控状态查询站点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCountryCon",value="站点的国控状态标识",dataType = "String")
    })

    @RequestMapping(value = "/queryStationsByCountryCon/{isCountryCon}", method = RequestMethod.GET)
    @ResponseBody
    public String queryStationsByCountryCon(@PathVariable String isCountryCon){

        if (!isCountryCon.equals("0") && !isCountryCon.equals("1")){
            System.out.println("参数格式不正确");
            return null;
        }

        String stationMessage = stationService.queryStationsByCountryCon(Integer.parseInt(isCountryCon));
        System.out.println("查询到的站点信息为");
        System.out.println(stationMessage);

        return stationMessage;
    }
}
