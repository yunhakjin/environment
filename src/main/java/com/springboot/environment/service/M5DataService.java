package com.springboot.environment.service;
import com.springboot.environment.bean.M5Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface M5DataService {
    public List<M5Data> getAll();
    public List<M5Data> getM5DataByData_id(String Data_id);

    String queryM5dataByStationIdAndDatetime(String stationId, String date);

    Map getM5StationsData(Map params);

    Map getmanyM5databystationanddata(Map params);
}

