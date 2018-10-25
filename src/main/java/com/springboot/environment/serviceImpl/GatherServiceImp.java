package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Gather;
import com.springboot.environment.dao.GatherDao;
import com.springboot.environment.service.GatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatherServiceImp implements GatherService {
    @Autowired
    private GatherDao gatherDao;

    @Override
    public List<Gather> getAllGather() {
        return gatherDao.findAll();
    }

    @Override
    public Gather getOneGather(String gather_id) {
        return gatherDao.getAllByGather_id(gather_id);
    }
}
