package com.springboot.environment.controller;

import com.springboot.environment.bean.Company;
import com.springboot.environment.bean.Operation;
import com.springboot.environment.service.CompanyService;
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
@RequestMapping("/company")
@Api("企业类api")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ApiOperation(value="查询所有的企业",notes = "查询所有企业")
    @RequestMapping(value = "/getallcompany",method = RequestMethod.GET)
    public List getAllCompany(){
        return companyService.getAllCompany();
    }

    @ApiOperation(value="新增企业信息")
    @ApiImplicitParam(value = "新增企业的json",name = "params",dataType = "JSON")
    @RequestMapping(value="/addcompany",method = RequestMethod.POST)
    public String addCompany(@RequestBody Map<String,String> params){
        String company_code=params.get("companyCode");
        String company_name=params.get("companyName");
        String company_type=params.get("companyType");
        String industry=params.get("industry");
        String company_place=params.get("companyPlace");
        String company_range=params.get("companyRange");
        String represent=params.get("represent");
        String represent_tel=params.get("representTel");
        String protection=params.get("protection");
        String protection_tel=params.get("protectionTel");
        Company company=new Company();
        company.setCompany_code(company_code);
        company.setCompany_name(company_name);
        company.setCompany_type(company_type);
        company.setIndustry(industry);
        company.setCompany_place(company_place);
        company.setCompany_range(company_range);
        company.setRepresent(represent);
        company.setRepresent_tel(represent_tel);
        company.setProtection(protection);
        company.setProtection_tel(protection_tel);
        if(!companyService.getOneCompany(company_code).isEmpty()){
            return "已存在此企业信息";
        }
        else{
            companyService.insertCompany(company);
        }
        return "success";
    }

    @ApiOperation(value = "删除企业信息")
    @ApiImplicitParam(value = "删除企业信息的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/deletecompany",method = RequestMethod.POST)
    public String deleteCompany(@RequestBody Map<String,String> params){
        String company_code=params.get("companyCode");
        companyService.deleteCompany(company_code);
        return "success";
    }

    @ApiOperation(value="更新企业信息")
    @ApiImplicitParam(value="更新企业信息的json",name = "params",dataType = "JSON")
    @RequestMapping(value = "updatecompany",method = RequestMethod.POST)
    public String updateCompany(@RequestBody Map<String,String> params){
        String company_code=params.get("companyCode");
        String company_name=params.get("companyName");
        String company_type=params.get("companyType");
        String industry=params.get("industry");
        String company_place=params.get("companyPlace");
        String company_range=params.get("companyRange");
        String represent=params.get("represent");
        String represent_tel=params.get("representTel");
        String protection=params.get("protection");
        String protection_tel=params.get("protectionTel");
        String target=params.get("target");
        Company company=new Company();
        company.setCompany_code(company_code);
        company.setCompany_name(company_name);
        company.setCompany_type(company_type);
        company.setIndustry(industry);
        company.setCompany_place(company_place);
        company.setCompany_range(company_range);
        company.setRepresent(represent);
        company.setRepresent_tel(represent_tel);
        company.setProtection(protection);
        company.setProtection_tel(protection_tel);
        companyService.updateCompany(company,target);
        return "success";
    }

    @ApiOperation(value = "企业的模糊搜索")
    @ApiImplicitParam(value = "企业模糊查询的json",name = "parmas",dataType = "JSON")
    @RequestMapping(value = "/getcompanylike",method = RequestMethod.POST)
    public List<Company> getCompanyLike(@RequestBody Map<String,String> params){
        String target=params.get("target");
        return companyService.getCompanyLike(target);
    }
}
