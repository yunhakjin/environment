package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.GatherData;
import com.springboot.environment.dao.GatherDataDao;
import com.springboot.environment.service.GatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatherDataServiceImp implements GatherDataService {
    @Autowired
    private GatherDataDao gatherDataDao;

    @Override
    public List<GatherData> getAllByGather_id(String GatherId) {
        return gatherDataDao.getAllByGather_id(GatherId);
    }

    @Override
    public List<GatherData> getAllByGather_idAndData_time(String GatherId, String Data_time) {
        return gatherDataDao.getAllByGather_idAndData_time(GatherId,Data_time);
    }
}
