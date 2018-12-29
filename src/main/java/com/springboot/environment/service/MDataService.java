package com.springboot.environment.service;
import com.springboot.environment.bean.MData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MDataService {
    public List<MData> getAll();
    public List<MData> getMDataByData_id(String Data_id);

    String queryMdataByStationIdAndDatetime(String stationId, String date);

    Map getMDataByStationsID();

    Map getmanyMdatabystationanddata(Map params);
}

