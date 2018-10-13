package com.springboot.environment.controller;

import com.springboot.environment.bean.Norm;
import com.springboot.environment.service.NormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/norm")
@Api("指标类相关api")
public class NormController {
    @Autowired
    private NormService normService;

    @ApiOperation(value="返回所有指标的信息",notes = "所有指标的信息")

    @GetMapping(value = "/getall")
    public List<Norm> getAll(){
        return normService.getAll();
    }

    @ApiOperation(value="增加某一个指标",notes = "需要定义指标代码(3,6位)，名称，状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "node_id_code",value="指标代码（3位）",dataType = "String",example = "leq"),
            @ApiImplicitParam(name = "node_code",value="指标代码",dataType = "String",example = "n23leq"),
            @ApiImplicitParam(name="node_name",value = "指标名称",dataType = "String",example = "平均声效值"),
            @ApiImplicitParam(name = "norm_status",value = "指标状态",dataType = "int",example = "0/1"),
            @ApiImplicitParam(name="application",value = "暂未使用")
    }
    )
    @PostMapping(value = "/addone/{norm_id_code}/{norm_code}/{norm_name}/{norm_status}/{application}")
    public void addOne(@PathVariable String norm_id_code,
                       @PathVariable String norm_code,
                       @PathVariable String norm_name,
                       @PathVariable int norm_status,
                       @PathVariable String application){
        Norm norm=new Norm();
        norm.setApplication(application);
        norm.setNorm_code(norm_code);
        norm.setNorm_id_code(norm_id_code);
        norm.setNorm_name(norm_name);
        norm.setNorm_status(norm_status);
        normService.addOne(norm);
    }

    @ApiOperation(value="修改某一个指标",notes = "需要定义指标代码(3,6位)，名称，状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "node_id_code",value="指标代码（3位）",dataType = "String",example = "leq"),
            @ApiImplicitParam(name = "node_code",value="指标代码",dataType = "String",example = "n23leq"),
            @ApiImplicitParam(name="node_name",value = "指标名称",dataType = "String",example = "平均声效值"),
            @ApiImplicitParam(name = "norm_status",value = "指标状态",dataType = "int",example = "0/1"),
            @ApiImplicitParam(name="application",value = "暂未使用")
    }
    )
    @PostMapping(value = "/updateone/{norm_id_code}/{norm_code}/{norm_name}/{norm_status}/{application}")
    public void updateOne(@PathVariable String norm_id_code,
                       @PathVariable String norm_code,
                       @PathVariable String norm_name,
                       @PathVariable int norm_status,
                       @PathVariable String application){
        Norm norm=new Norm();
        norm.setNorm_id_code(norm_id_code);
        norm.setNorm_code(norm_code);
        norm.setNorm_name(norm_name);
        norm.setNorm_status(norm_status);
        norm.setApplication(application);
        normService.updateOne(norm_id_code,norm);
   }

    @ApiOperation(value="删除某一个指标",notes = "根据指标的短码进行指标删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "node_id_code",value="指标代码（3位）",dataType = "String",example = "leq")
    }
    )
    @DeleteMapping(value="/deleteone/{norm_id_code}")
    public void deleteOne(@PathVariable String norm_id_code){
        normService.delOne(norm_id_code);
    }


}
