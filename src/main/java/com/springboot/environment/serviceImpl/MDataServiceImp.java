package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.MData;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.service.MDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MDataServiceImp implements MDataService {
    @Autowired
    private MDataDao mDataDao;

    @Override
    public List<MData> getAll() {
        return mDataDao.findAll();
    }

    @Override
    public Page<MData> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.findAll(pageable);
    }

    @Override
    public List<MData> getMDataByData_id(String Data_id) {
        return mDataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<MData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public String queryMdataByStationIdAndDatetime(String stationId, String date) {
        return null;
    }
}
