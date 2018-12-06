package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="m5data")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class M5Data implements Serializable {
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

    @Column(name = "data_id", length=32)
    private String data_id;

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getData_id() {
        return data_id;
    }

    @Column(name="data_time")
    private Date data_time;

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }

    public Date getData_time() {
        return data_time;
    }

    @Column(name="station_id",length=20)
    private String station_id;

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_id() {
        return station_id;
    }

    @Column(name="create_time")
    private Date create_time;

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    @Column(name="data_status",length=1)
    private int data_status;

    public void setData_status(int data_status) {
        this.data_status = data_status;
    }

    public int getData_status() {
        return data_status;
    }

    @Column(name="data_check",length = 1)
    private int data_check;

    public void setData_check(int data_check) {
        this.data_check = data_check;
    }

    public int getData_check() {
        return data_check;
    }

    @Column(name = "norm_code",length = 10)
    private String norm_code;

    public void setNorm_code(String norm_code) {
        this.norm_code = norm_code;
    }

    public String getNorm_code() {
        return norm_code;
    }

    @Column(name="norm_val",length=10)
    private String norm_val;

    public void setNorm_val(String norm_val) {
        this.norm_val = norm_val;
    }

    public String getNorm_val() {
        return norm_val;
    }

    @Column(name = "norm_val_check",length=10)
    private String norm_val_check;

    public void setNorm_val_check(String norm_val_check) {
        this.norm_val_check = norm_val_check;
    }

    public String getNorm_val_check() {
        return norm_val_check;
    }

    @Column(name="norm_flag",length=10)
    private String norm_flag;

    public void setNorm_flag(String norm_flag) {
        this.norm_flag = norm_flag;
    }

    public String getNorm_flag() {
        return norm_flag;
    }

    @Column(name = "norm_sptime",length = 20)
    private String norm_sptime;

    public void setNorm_sptime(String norm_sptime) {
        this.norm_sptime = norm_sptime;
    }

    public String getNorm_sptime() {
        return norm_sptime;
    }

    @Column(name ="norm_vdr",length = 10)
    private String norm_vdr;

    public void setNorm_vdr(String norm_vdr) {
        this.norm_vdr = norm_vdr;
    }

    public String getNorm_vdr() {
        return norm_vdr;
    }
}
