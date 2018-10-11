package com.springboot.environment.serviceImpl;

import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.service.DDataService;
import org.springframework.beans.factory.annotation.Autowired;

public class DDataServiceImp implements DDataService {

    @Autowired
    private DDataDao dDataDao;


}
