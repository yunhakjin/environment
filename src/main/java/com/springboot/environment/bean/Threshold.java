package com.springboot.environment.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "threshold")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
/**
 * 噪声站点超标报警阈值设置表
 * target_street:报警目标(街道) 与station中的street相对应
 * target_area:报警目标(区域环境)与station中的area相对应
 * target_domain:报警目标(功能区)与station中的domain相对应
 * manager:负责人(与user表的id进行对应)
 * norm_code:超标指标(与norm表中的norm_code相对应)
 * d_limit:昼阈值
 * n_limit：夜阈值*/
public class Threshold implements Serializable {
    private static final long serialVersionUID = -509438491019594820L;
    @Id
    @GeneratedValue
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Column(name = "target_street")
    private String target_street;

    public void setTarget_street(String target_street) {
        this.target_street = target_street;
    }

    public String getTarget_street() {
        return target_street;
    }

    @Column(name = "target_area")
    private int target_area;

    public int getTarget_area() {
        return target_area;
    }

    public void setTarget_area(int target_area) {
        this.target_area = target_area;
    }

    @Column(name = "target_domain")
    private int target_domain;

    public void setTarget_domain(int target_domain) {
        this.target_domain = target_domain;
    }

    public int getTarget_domain() {
        return target_domain;
    }

    @Column(name="manager")
    private Integer manager;

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public Integer getManager() {
        return manager;
    }

    @Column(name = "norm_code")
    private String norm_code;

    public void setNorm_code(String norm_code) {
        this.norm_code = norm_code;
    }

    public String getNorm_code() {
        return norm_code;
    }

    @Column(name = "d_limit")
    private  String d_limit;

    public void setD_limit(String d_limit) {
        this.d_limit = d_limit;
    }

    public String getD_limit() {
        return d_limit;
    }

    @Column(name = "n_limit")
    private String n_limit;

    public void setN_limit(String n_limit) {
        this.n_limit = n_limit;
    }

    public String getN_limit() {
        return n_limit;
    }
}

