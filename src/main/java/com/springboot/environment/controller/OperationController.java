package com.springboot.environment.controller;

import com.springboot.environment.bean.Operation;
import com.springboot.environment.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operation")
@Api(value = "维护单位类api")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @ApiOperation(value = "查询所有的运维单位")
    @RequestMapping(value = "/getalloperation",method = RequestMethod.GET)
    public List<Operation> getAllOperation(){
        return operationService.getAllOperation();
    }
    
}
