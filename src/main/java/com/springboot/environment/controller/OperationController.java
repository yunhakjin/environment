package com.springboot.environment.controller;

import com.springboot.environment.bean.Operation;
import com.springboot.environment.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @ApiOperation(value="增加运维单位")
    @ApiImplicitParam(value = "新增运维单位的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/addoperation",method = RequestMethod.POST)
    public String addOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        String operation_name=params.get("operationName");
        String operation_relate=params.get("operationRelate");
        String operation_tel=params.get("operationTel");
        String operation_target=params.get("operationTarget");
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
        return "success";
    }

    @ApiOperation(value="删除运维单位")
    @ApiImplicitParam(value = "删除运维单位的json",name = "params",dataType = "JSON")
    @RequestMapping(value="/deleteoperation",method = RequestMethod.POST)
    public String delOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        operationService.deleteOperation(operation_id);
        return "success";
    }

    @ApiOperation(value="修改运维单位")
    @ApiImplicitParam(value="修改运维单位的json",name = "params",dataType = "JSON")
    @RequestMapping(value = "/updateoperation",method = RequestMethod.POST)
    public String updateOperation(@RequestBody Map<String,String> params){
        String operation_id=params.get("operationId");
        String operation_name=params.get("operationName");
        String operation_relate=params.get("operationRelate");
        String operation_tel=params.get("operationTel");
        String operation_target=params.get("operationTarget");
        String target=params.get("target");
        Operation operation=new Operation();
        operation.setOperation_id(operation_id);
        operation.setOperation_name(operation_name);
        operation.setOperation_relate(operation_relate);
        operation.setOperation_tel(operation_tel);
        operation.setOperation_target(operation_target);
        operationService.updateOperation(operation,target);
        return "success";
    }

    @ApiOperation(value = "运维单位的模糊搜索")
    @ApiImplicitParam(value = "运维单位模糊查询的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/getoperationlike",method = RequestMethod.POST)
    public List<Operation> getOperationLike(@RequestBody Map<String,String> params){
        String target=params.get("target");
        return operationService.getOperationLike(target);
    }
}
