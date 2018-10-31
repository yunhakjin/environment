package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.M5Data;
import com.springboot.environment.dao.M5DataDao;
import com.springboot.environment.service.M5DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class M5DataServiceImp implements M5DataService {
    @Autowired
    private M5DataDao m5DataDao;

    @Override
    public List<M5Data> getAll() {
        return m5DataDao.findAll();
    }

    @Override
    public Page<M5Data> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return m5DataDao.findAll(pageable);
    }

    @Override
    public List<M5Data> getM5DataByData_id(String Data_id) {
        return m5DataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<M5Data> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return m5DataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public String queryM5dataByStationIdAndDatetime(String stationId, String date) {
        return null;
    }
}

