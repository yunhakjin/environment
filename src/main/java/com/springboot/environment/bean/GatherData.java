package com.springboot.environment.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "gatherdata")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//采集车采集噪声数据类：
//        id	主键	int(18)	自增
//        data_id	数据唯一标识	varchar(32)
//        data_time	数据时间	datetime
//        gather_id	采集车编号	varchar(20)	与站点表对应
//        create_date	接受时间	datetime
//        data_status	数据状态	int(1)	1 在线数据 0 离线数据
//        data_check	数据审核状态	int(1)	0 已审核 1 未审核
//        position	采集车所处位置	point	“(x,y)”的形式
//        norm_code	指标代码	varchar(10)
//        norm_val	指标数值	varchar(10)
//        norm_flag	指标是否有效	varchar(10)
//        norm_sptime		varchar(32)
//        norm_val_check	修改后的指标数值	varchar(10)

public class GatherData {
    @Id
    @GeneratedValue
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Column(name = "data_id",length = 32,nullable = false)
    private String data_id;

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getData_id() {
        return data_id;
    }

    @Column(name = "data_time")
    private Date data_time;

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }

    public Date getData_time() {
        return data_time;
    }

    @Column(name = "gather_id",length = 20,nullable = false)
    private String gather_id;

    public void setGather_id(String gather_id) {
        this.gather_id = gather_id;
    }

    public String getGather_id() {
        return gather_id;
    }

    @Column(name="create_time")
    private Date create_time;

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    @Column(name="data_status",length = 1,nullable = false)
    private int data_status;

    public void setData_status(int data_status) {
        this.data_status = data_status;
    }

    public int getData_status() {
        return data_status;
    }

    @Column(name = "data_check",length = 1)
    private int data_check;

    public void setData_check(int data_check) {
        this.data_check = data_check;
    }

    public int getData_check() {
        return data_check;
    }

    @Column(name = "gather_position",length = 100,nullable = false)
    private String gather_position;
    public void setGather_position(String gather_position) {
        this.gather_position = gather_position;
    }

    public String getGather_position() {
        return gather_position;
    }

    @Column(name = "norm_code",length = 10,nullable = false)
    private String norm_code;

    public void setNorm_code(String norm_code) {
        this.norm_code = norm_code;
    }

    public String getNorm_code() {
        return norm_code;
    }

    @Column(name = "norm_val",length = 10,nullable = false)
    private String norm_val;

    public void setNorm_val(String norm_val) {
        this.norm_val = norm_val;
    }

    public String getNorm_val() {
        return norm_val;
    }

    @Column(name = "norm_flag",length = 10)
    private String norm_flag;

    public void setNorm_flag(String norm_flag) {
        this.norm_flag = norm_flag;
    }

    public String getNorm_flag() {
        return norm_flag;
    }

    @Column(name = "norm_sptime",length = 32)
    private String norm_sptime;

    public void setNorm_sptime(String norm_sptime) {
        this.norm_sptime = norm_sptime;
    }

    public String getNorm_sptime() {
        return norm_sptime;
    }

    @Column(name = "norm_val_check",length = 10)
    private String norm_val_check;

    public void setNorm_val_check(String norm_val_check) {
        this.norm_val_check = norm_val_check;
    }

    public String getNorm_val_check() {
        return norm_val_check;
    }
}

