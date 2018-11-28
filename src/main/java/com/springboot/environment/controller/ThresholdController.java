package com.springboot.environment.controller;

import com.springboot.environment.bean.Threshold;
import com.springboot.environment.service.ThresholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(value = "/threshold")
@Api("报警阈值设置api")
public class ThresholdController {
    @Autowired
    private ThresholdService thresholdService;

    @ApiOperation(value = "获取报警阈值,按照功能区以及指标代码")
    @ApiImplicitParam(name = "params",value="包含功能区以及指标代码",dataType = "JSON",example = "{'domain':'1','norm_code':'n00006'}")
    @RequestMapping(value = "/getbydomain",method = RequestMethod.POST)
    public Threshold getThresholdByDomain(@RequestBody Map<String,String> parmas){
        int domain=Integer.valueOf(parmas.get("domain"));
        String norm_code=parmas.get("norm_code");
        return thresholdService.getThresholdByDomain(domain,norm_code);
    }

    @ApiOperation(value="增加阈值设置")
    @ApiImplicitParam(name="params",value = "包含阈值设置元素的json",dataType = "JSON")
    @RequestMapping(value = "/insertthreshold",method = RequestMethod.POST)
    public String insertThreshold(@RequestBody Map<String,String> params){
        String target_street=params.get("target_street");
        int target_area=Integer.valueOf(params.get("target_area"));
        int target_domain=Integer.valueOf(params.get("target_domain"));
        int manager=Integer.valueOf(params.get("manager"));
        String norm_code=params.get("norm_code");
        String d_limit=params.get("d_limit");
        String n_limit=params.get("n_limit");
        if(thresholdService.getThresholdByDomain(target_domain,norm_code)!=null){
            return "已经存在该功能区此指标的阈值";
        }
        Threshold threshold=new Threshold();
        threshold.setTarget_street(target_street);
        threshold.setTarget_area(target_area);
        threshold.setTarget_domain(target_domain);
        threshold.setManager(manager);
        threshold.setD_limit(d_limit);
        threshold.setN_limit(n_limit);
        threshold.setNorm_code(norm_code);
        thresholdService.insertThreshold(threshold);
        return "成功插入";
    }

    @ApiOperation(value="修改阈值设置")
    @ApiImplicitParam(name="params",value = "包含阈值设置元素的json",dataType = "JSON")
    @RequestMapping(value = "/updatethreshold",method = RequestMethod.POST)
    public String updateThreshold(@RequestBody Map<String,String> params){
        String target_street=params.get("target_street");
        int target_area=Integer.valueOf(params.get("target_area"));
        int target_domain=Integer.valueOf(params.get("target_domain"));
        int manager=Integer.valueOf(params.get("manager"));
        String norm_code=params.get("norm_code");
        String d_limit=params.get("d_limit");
        String n_limit=params.get("n_limit");
        int id=Integer.valueOf(params.get("id"));
        Threshold threshold=new Threshold();
        threshold.setTarget_street(target_street);
        threshold.setTarget_area(target_area);
        threshold.setTarget_domain(target_domain);
        threshold.setManager(manager);
        threshold.setD_limit(d_limit);
        threshold.setN_limit(n_limit);
        threshold.setNorm_code(norm_code);
        thresholdService.updateThreshold(threshold,id);
        return "成功修改";
    }

    @ApiOperation(value="删除阈值设置")
    @ApiImplicitParam(name="params",value = "包含要删除id的json",dataType = "JSON")
    @RequestMapping(value = "/deletethreshold",method = RequestMethod.POST)
    public String deleteThreshold(@RequestBody Map<String,String> params){
        int id=Integer.valueOf(params.get("id"));
        thresholdService.deleteThreshold(id);
        return "成功删除";
    }


}
