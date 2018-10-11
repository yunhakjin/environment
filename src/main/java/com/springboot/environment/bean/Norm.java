package com.springboot.environment.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "norm")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
/*指标类
* norm_id_code:指标代码（3位） 用作标记ID
* norm_code: 指标代码(6位)
* norm_name: 指标名称
* norm_status:指标状态 有效 1 无效 0
* application:所属应用（暂未使用）*/
public class Norm {
    @Id
    @Column(name="norm_id_code")
    private String norm_id_code;

    public void setNorm_id_code(String norm_id_code) {
        this.norm_id_code = norm_id_code;
    }
    public String getNorm_id_code() {
        return norm_id_code;
    }

    @Column(name="norm_code")
    private String norm_code;

    public void setNorm_code(String norm_code){
        this.norm_code=norm_code;
    }
    public String getNorm_code(){
        return norm_code;
    }

    @Column(name="norm_name")
    private String norm_name;

    public void setNorm_name(String norm_name) {
        this.norm_name = norm_name;
    }

    public String getNorm_name() {
        return norm_name;
    }

    @Column(name="norm_status")
    private int norm_status;

    public void setNorm_status(int norm_status) {
        this.norm_status = norm_status;
    }

    public int getNorm_status() {
        return norm_status;
    }

    @Column(name="application")
    private String application;

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplication() {
        return application;
    }
}
