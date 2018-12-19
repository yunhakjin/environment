package com.springboot.environment.controller;

import com.springboot.environment.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@Api("企业类api")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ApiOperation(value="查询所有的企业",notes = "查询所有企业")
    @RequestMapping(value = "getallcompany",method = RequestMethod.GET)
    public List getAllCompany(){
        return companyService.getAllCompany();
    }

}
