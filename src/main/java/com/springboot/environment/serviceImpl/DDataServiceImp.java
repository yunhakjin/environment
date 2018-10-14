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

}
