package com.springboot.environment.service;

import com.springboot.environment.bean.Company;

import java.util.List;

public interface CompanyService {
    public List<Company> getAllCompany();
    public void insertCompany(Company company);
    public void deleteCompany(String company_code);
    public void updateCompany(Company company,String target);
}
