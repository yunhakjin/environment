package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sts on 2018/11/25.
 */

@Data
@Entity
@Table(name="warning")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Warning {
    private static final long serialVersionUID = -509438491019594820L;

    @Id
    @GeneratedValue
    private Integer warning_id;
    public Integer getWarning_id() { return warning_id; }


    @Column(name="station_id",length=20)
    private String station_id;
    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }
    public String getStation_id() {
        return station_id;
    }


    @Column(name = "station_name", length=50)
    private String station_name;
    public String getStation_name() { return station_name; }
    public void setStation_name(String station_name) { this.station_name = station_name; }


    @Column(name="warning_end_time")
    private Date warning_end_time;
    public void setWarning_end_time(Date warning_end_time) {
        this.warning_end_time = warning_end_time;
    }
    public Date getWarning_end_time() {
        return warning_end_time;
    }

    @Column(name="warning_start_time")
    private Date warning_start_time;
    public void setWarning_start_time(Date warning_start_time) {
        this.warning_start_time = warning_start_time;
    }
    public Date getWarning_start_time() {
        return warning_start_time;
    }


    @Column(name = "warning_domain",length = 1)
    private int warning_domain;
    public void setWarning_domain(int warning_domain) {
        this.warning_domain = warning_domain;
    }
    public int getWarning_domain() {
        return warning_domain;
    }

    @Column(name = "warning_district",length = 45)
    private String warning_district;
    public void setWarning_district(String warning_district) { this.warning_district = warning_district; }
    public String getWarning_district() { return warning_district; }


    @Column(name="threshold",length=10)
    private String limit_value;
    public void setLimit_value(String limit_value) {
        this.limit_value = limit_value;
    }
    public String getLimit_value() {
        return limit_value;
    }




    @Column(name="leq",length=10)
    private String real_value;
    public void setReal_value(String limit_value) {
        this.real_value = real_value;
    }
    public String getReal_value() {
        return real_value;
    }


//    @Column(name = "limit_type",length = 1)
//    private int limit_type;
//    public int getLimit_type() { return limit_type; }
//    public void setLimit_type(int limit_type) { this.limit_type = limit_type; }


    @Column(name = "norm_code", length = 10)
    private String norm_code;
    public String getNorm_code() { return norm_code; }
    public void setNorm_code(String norm_code) { this.norm_code = norm_code; }


    @Column(name = "norm_name", length = 5)
    private String norm_name;
    public String getNorm_name() { return norm_name; }
    public void setNorm_name(String norm_name) { this.norm_name = norm_name; }


    @Column(name = "manger_tel")
    private String manger_tel;
    public String getManger_tel() { return  manger_tel; }
    public void  setManger_tel(String manger_tel) { this.manger_tel = manger_tel; }

    @Column(name = "cal")
    private String cal;
    public String getCal() { return cal; }
    public void setCal(String cal) { this.cal = cal; }

    @Column(name = "lmx")
    private String lmx;
    public String getLmx() { return lmx; }
    public void setLmx(String lmx) { this.lmx = lmx; }

    @Column(name = "sd")
    private String sd;
    public String getSd() { return sd; }
    public void setSd(String sd) { this.sd = sd; }

    @Column(name = "vdr")
    private int vdr;
    public int getVdr() { return vdr; }
    public void setVdr(int vdr) {this.vdr = vdr; }

}
