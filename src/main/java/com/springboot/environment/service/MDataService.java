package com.springboot.environment.service;
import com.springboot.environment.bean.MData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MDataService {
    public List<MData> getAll();
    public Page<MData> getAllPage(int page, int size);
    public List<MData> getMDataByData_id(String Data_id);
    public Page<MData> getByStationAndTime(String station_id,String starttime,String endtime,int data_check,int data_status,int page,int size);
}

