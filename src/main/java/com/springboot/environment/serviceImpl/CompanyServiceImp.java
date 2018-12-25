package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Company;
import com.springboot.environment.dao.CompanyDao;
import com.springboot.environment.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImp implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> getAllCompany(){
       return companyDao.getAllCompany();
    }

    @Override
    public void insertCompany(Company company){
        String company_code=company.getCompany_code();
        String company_name=company.getCompany_name();
        String company_type=company.getCompany_type();
        String industry=company.getIndustry();
        String company_place=company.getCompany_place();
        String company_range=company.getCompany_range();
        String represent=company.getRepresent();
        String represent_tel=company.getRepresent_tel();
        String protection=company.getProtection();
        String protection_tel=company.getProtection_tel();
        companyDao.insertCompany(company_code,company_name,company_type,industry,company_place,company_range,represent,represent_tel,protection,protection_tel);
    }

    @Override
    public void deleteCompany(String company_code){
       companyDao.deleteCompany(company_code);
    }

    @Override
    public void updateCompany(Company company,String target){
        String company_code=company.getCompany_code();
        String company_name=company.getCompany_name();
        String company_type=company.getCompany_type();
        String industry=company.getIndustry();
        String company_place=company.getCompany_place();
        String company_range=company.getCompany_range();
        String represent=company.getRepresent();
        String represent_tel=company.getRepresent_tel();
        String protection=company.getProtection();
        String protection_tel=company.getProtection_tel();
        companyDao.updateCompany(company_code,company_name,company_type,industry,company_place,company_range,represent,represent_tel,protection,protection_tel,target);
    }

    @Override
    public List<Company> getOneCompany(String company_code){
        return companyDao.getOneCompany(company_code);
    }

    @Override
    public List<Company> getCompanyLike(String target){
        return companyDao.getCompanyLike(target);
    }

}
