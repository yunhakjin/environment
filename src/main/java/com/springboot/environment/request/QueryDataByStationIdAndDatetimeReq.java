package com.springboot.environment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryDataByStationIdAndDatetimeReq {

    @JSONField(name = "station_id")
    private String stationId;

    @JSONField(name = "data_type")
    private int dataType;

    @JSONField(name = "date")
    private String date;

    public QueryDataByStationIdAndDatetimeReq() {
    }

    public QueryDataByStationIdAndDatetimeReq(String stationId, int dataType, String date) {
        this.stationId = stationId;
        this.dataType = dataType;
        this.date = date;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "QueryDataByStationIdAndDatetimeReq{" +
                "stationId='" + stationId + '\'' +
                ", dataType=" + dataType +
                ", date='" + date + '\'' +
                '}';
    }
}
