package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Threshold;
import com.springboot.environment.dao.ThresholdDao;
import com.springboot.environment.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ThresholdServiceImp implements ThresholdService {
    @Autowired
    private ThresholdDao thresholdDao;

    @Override
    public Threshold getThresholdByDomain(int target_domain, String norm_code) {
        return thresholdDao.getThresholdByDomain(target_domain, norm_code);
    }

    @Override
    public void insertThreshold(Threshold threshold) {
        String target_street = threshold.getTarget_street();
        int target_area = threshold.getTarget_area();
        int target_domain = threshold.getTarget_domain();
        int manager = threshold.getManager();
        String norm_code = threshold.getNorm_code();
        String d_limit = threshold.getD_limit();
        String n_limit = threshold.getN_limit();
        thresholdDao.insertThreshold(target_street, target_area, target_domain, manager, norm_code, d_limit, n_limit);
    }

    @Override
    public void deleteThreshold(int id) {
        thresholdDao.deleteThreshold(id);
    }

    @Override
    public void updateThreshold(Threshold threshold, int id) {
        String target_street = threshold.getTarget_street();
        int target_area = threshold.getTarget_area();
        int target_domain = threshold.getTarget_domain();
        int manager = threshold.getManager();
        String norm_code = threshold.getNorm_code();
        String d_limit = threshold.getD_limit();
        String n_limit = threshold.getN_limit();
        thresholdDao.updateThreshold(target_street, target_area, target_domain, manager, norm_code, d_limit, n_limit,id);

    }
}
