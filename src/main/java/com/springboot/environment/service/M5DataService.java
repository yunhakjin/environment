package com.springboot.environment.service;
import com.springboot.environment.bean.M5Data;
import org.springframework.data.domain.Page;

import java.util.List;

public interface M5DataService {
    public List<M5Data> getAll();
    public Page<M5Data> getAllPage(int page, int size);
    public List<M5Data> getM5DataByData_id(String Data_id);
    public Page<M5Data> getByStationAndTime(String station_id,String starttime,String endtime,int data_check,int data_status,int page,int size);

    String queryM5dataByStationIdAndDatetime(String stationId, String date);
}

