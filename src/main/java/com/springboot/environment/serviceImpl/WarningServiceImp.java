package com.springboot.environment.serviceImpl;

/**
 * Created by sts on 2018/11/26.
 */
import com.springboot.environment.service.WarningService;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.WarningDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningServiceImp implements WarningService {

    @Autowired
    private WarningDao warningDao;

    @Override
    public Page<Warning> getAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return warningDao.findAll(pageable);
    }

    @Override
    public Page<Warning> queryWarningByDomainAndTime(String warning_district,int warning_domain, String start_time, String end_time, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return warningDao.queryWarningByDomainAndTime(warning_district,warning_domain,start_time,end_time,pageable);
    }

    @Override
    public List<Warning> queryNewWarning(int lastNum ) {
        return warningDao.queryNewWarning(lastNum);
    }

    @Override
    public int getCount() {
        return warningDao.getCount();
    }


}
