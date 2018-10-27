package com.springboot.environment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryStationsByKeyReq {

    @JSONField(name = "key")
    private String key;

    public QueryStationsByKeyReq(String key) {
        this.key = key;
    }

    public QueryStationsByKeyReq(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "QueryStationsByKeyReq{" +
                "key='" + key + '\'' +
                '}';
    }
}
