package com.springboot.environment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class QuerymDataByStationsAreaReq {

    @JSONField(name = "area_id")
    private int area;

    @JSONField(name = "each_page_num")
    private int pageSize;

    @JSONField(name = "current_page")
    private int pageNum;

    public QuerymDataByStationsAreaReq() {
    }

    public QuerymDataByStationsAreaReq(int area, int pageSize, int pageNum) {
        this.area = area;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
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

    @Override
    public String toString() {
        return "QuerymDataByStationsAreaReq{" +
                "area=" + area +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }
}
