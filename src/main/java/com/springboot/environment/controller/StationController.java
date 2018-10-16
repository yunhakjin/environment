package com.springboot.environment.controller;

import com.springboot.environment.service.StationService;
import io.swagger.annotations.Api;
import javafx.scene.chart.ValueAxis;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api("站点信息类相关api")
@RequestMapping(value = "/station")
public class StationController {

    @Resource
    StationService stationService;

    
}
