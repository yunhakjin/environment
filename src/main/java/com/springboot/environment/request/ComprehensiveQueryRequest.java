package com.springboot.environment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class ComprehensiveQueryRequest {

    //功能区
    @JSONField(name = "area_id")
    private String area;

    //区域环境
    @JSONField(name = "environment")
    private String environment;

    @JSONField(name = "isArea")
    private String isArea;

    @JSONField(name = "isCity")
    private String isCity;

    @JSONField(name = "isCountry")
    private String isCountry;

    //站点状态 在线离线
    @JSONField(name = "state")
    private String state;

    //站点手动自动标识
    @JSONField(name = "attribute")
    private String attribute;

    //行政区
    @JSONField(name = "district")
    private String district;


    public ComprehensiveQueryRequest(String area, String environment, String isArea, String isCity, String isCountry, String state, String attribute, String district, String street, int pageSize, int pageNum) {
        this.area = area;
        this.environment = environment;
        this.isArea = isArea;
        this.isCity = isCity;
        this.isCountry = isCountry;
        this.state = state;
        this.attribute = attribute;
        this.district = district;
        this.street = street;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    //道路
    @JSONField(name = "street")
    private String street;

    @JSONField(name = "each_page_num")
    private int pageSize;

    @JSONField(name = "current_page")
    private int pageNum;

    @JSONField(name = "user_operation_id")
    private String userOperationId;

    public String getUserOperationId() {
        return userOperationId;
    }

    public void setUserOperationId(String userOperationId) {
        this.userOperationId = userOperationId;
    }

    @Override
    public String toString() {
        return "ComprehensiveQueryRequest{" +
                "area='" + area + '\'' +
                ", environment='" + environment + '\'' +
                ", isArea='" + isArea + '\'' +
                ", isCity='" + isCity + '\'' +
                ", isCountry='" + isCountry + '\'' +
                ", state='" + state + '\'' +
                ", attribute='" + attribute + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", userOperationId='" + userOperationId + '\'' +
                '}';
    }

    public ComprehensiveQueryRequest() {
    }

    public ComprehensiveQueryRequest(String area, int pageSize, int pageNum, String userOperationId) {
        this.area = area;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.userOperationId = userOperationId;
    }

    public ComprehensiveQueryRequest(String area, String environment, String state, String attribute, String district, String street, int pageSize, int pageNum, String userOperationId) {
        this.area = area;
        this.environment = environment;
        this.state = state;
        this.attribute = attribute;
        this.district = district;
        this.street = street;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.userOperationId = userOperationId;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getIsArea() {
        return isArea;
    }

    public void setIsArea(String isArea) {
        this.isArea = isArea;
    }

    public String getIsCity() {
        return isCity;
    }

    public void setIsCity(String isCity) {
        this.isCity = isCity;
    }

    public String getIsCountry() {
        return isCountry;
    }

    public void setIsCountry(String isCountry) {
        this.isCountry = isCountry;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
