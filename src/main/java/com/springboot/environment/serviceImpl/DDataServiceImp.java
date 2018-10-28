package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.DData;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.service.DDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DDataServiceImp implements DDataService {

    @Autowired
    private DDataDao dDataDao;

    @Override
    public List<DData> getAll() {
        return dDataDao.findAll();
    }

    @Override
    public Page<DData> getAllPage(int page,int size){
        Pageable pageable=PageRequest.of(page,size);
        return dDataDao.findAll(pageable);
    }

    @Override
    public List<DData> getDDataByData_id(String Data_id) {
          return dDataDao.getAllByData_id(Data_id);
    }

    @Override
    public void saveDData(DData dData) {
        dDataDao.saveAndFlush(dData);
    }

    @Override
    public void delDData(String Data_id) {
        dDataDao.deleteAll(getDDataByData_id(Data_id));
    }

    @Override
    public Page<DData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return dDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public List<DData> getByStationAndMonth(String station_id, String month) {
        return dDataDao.getByStationAndMonth(station_id,month);
    }

    @Override
    public List<DData> getByStationAndDay(String station_id, String date) {
        return dDataDao.getByStationAndDay(station_id,date);
    }
}
