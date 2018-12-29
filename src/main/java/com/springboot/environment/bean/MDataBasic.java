package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@Entity
@Table(name="tb_data_basic_info")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class MDataBasic implements Serializable {

    @Id
    @GeneratedValue
    private BigInteger id;

    @Column(name = "DATA_ID")
    private String dataId;

    @Column(name = "DATA_TIME")
    private String dataTime;

    @Column(name = "STATION_ID")
    private String stationId;

    @Column(name = "DATA")
    private String data;

    @Column(name = "CREATE_DATE")
    private String createDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "MDataBasic{" +
                "id=" + id +
                ", dataId='" + dataId + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", stationId='" + stationId + '\'' +
                ", data='" + data + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
