package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.HData;
import com.springboot.environment.dao.HDataDao;
import com.springboot.environment.service.HDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HDataServiceImp implements HDataService {
    @Autowired
    private HDataDao hDataDao;

    @Override
    public List<HData> getAll() {
        return hDataDao.findAll();
    }

    @Override
    public Page<HData> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return hDataDao.findAll(pageable);
    }

    @Override
    public List<HData> getHDataByData_id(String Data_id) {
        return hDataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<HData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return hDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public List<HData> getByStationAndDate(String station_id, String date) {
        return hDataDao.getByStationAndDate(station_id,date);
    }
}
