package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="company")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Company {

    @Id
    @Column(name = "company_code")
    /**企业代码*/
    private String company_code;

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_code() {
        return company_code;
    }

    @Column(name = "company_name")
    /**企业名称*/
    private String company_name;

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    @Column(name = "company_type")
    /**企业类型*/
    private String company_type;

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public String getCompany_type() {
        return company_type;
    }

    @Column(name = "industry")
    /**所属行业*/
    private String industry;

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
    }

    @Column(name = "company_place")
    /**企业地址*/
    private String company_place;

    public void setCompany_place(String company_place) {
        this.company_place = company_place;
    }

    public String getCompany_place() {
        return company_place;
    }

    @Column(name = "company_range")
    /**企业规模*/
    private String company_range;

    public void setCompany_range(String company_range) {
        this.company_range = company_range;
    }

    public String getCompany_range() {
        return company_range;
    }

    @Column(name = "represent")
    /**法人代表*/
    private String represent;

    public void setRepresent(String represent) {
        this.represent = represent;
    }

    public String getRepresent() {
        return represent;
    }

    @Column(name = "represent_tel")
    /**联系电话*/
    private String represent_tel;

    public void setRepresent_tel(String represent_tel) {
        this.represent_tel = represent_tel;
    }

    public String getRepresent_tel() {
        return represent_tel;
    }

    @Column(name = "protection")
    /**环保负责人*/
    private String protection;

    public void setProtection(String protection) {
        this.protection = protection;
    }

    public String getProtection() {
        return protection;
    }

    @Column(name = "protection_tel")
    /**负责人电话*/
    private String protection_tel;

    public void setProtection_tel(String protection_tel) {
        this.protection_tel = protection_tel;
    }

    public String getProtection_tel() {
        return protection_tel;
    }
}
