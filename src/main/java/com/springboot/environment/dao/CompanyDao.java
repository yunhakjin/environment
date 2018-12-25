package com.springboot.environment.dao;

import com.springboot.environment.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
public interface CompanyDao extends JpaRepository<Company,String> {
    @Query(value = "select * from company c",nativeQuery = true)
    public List<Company> getAllCompany();

    @Transactional
    @Modifying
    @Query(value="insert into company(company_code,company_name,company_type,industry,company_place," +
            "company_range,represent,represent_tel,protection,protection_tel) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)",nativeQuery = true)
    public void insertCompany(String company_code,String company_name,String company_type,String industry,String company_place,
                              String company_range,String represent,String represent_tel,String protection,String protection_tel);

    @Transactional
    @Modifying
    @Query(value="delete from company where company_code=?1",nativeQuery = true)
    public void deleteCompany(String company_code);

    @Transactional
    @Modifying
    @Query(value = "update company set company_code=?1,company_name=?2,company_type=?3,industry=?4,company_place=?5," +
            "company_range=?6,represent=?7,represent_tel=?8,protection=?9,protection_tel=?10 where company_code=?11",nativeQuery = true)
    public void updateCompany(String company_code,String company_name,String company_type,String industry,String company_place,
                              String company_range,String represent,String represent_tel,String protection,String protection_tel,String target);

    @Query(value = "select * from company where company_code=?1",nativeQuery = true)
    public List<Company> getOneCompany(String company_code);

    @Query(value = "select * from company where company_code like %?1% or company_name like %?1%",nativeQuery = true)
    public List<Company> getCompanyLike(String target);
}
