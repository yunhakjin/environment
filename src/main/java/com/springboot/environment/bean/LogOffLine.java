package com.springboot.environment.bean;

/**
 * Created by yww on 2018/12/28.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "log_off_line")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class LogOffLine {
    @Id
    @Column(name = "id")
    private String logOffLineid;

    @Column(name = "station_id")
    private String station_id;

    @Column(name = "begin_time")
    private Date begin_time;


    @Column(name = "end_time")
    private Date end_time;


    @Column(name = "flag")
    private int flag;


    public String getLogOffLineid() {
        return logOffLineid;
    }

    public void setLogOffLineid(String logOffLineid) {
        this.logOffLineid = logOffLineid;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "LogOffLine{" +
                "logOffLineid='" + logOffLineid + '\'' +
                ", station_id='" + station_id + '\'' +
                ", begin_time=" + begin_time +
                ", end_time=" + end_time +
                ", flag=" + flag +
                '}';
    }
}

