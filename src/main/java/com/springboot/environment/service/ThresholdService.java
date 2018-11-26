package com.springboot.environment.service;

import com.springboot.environment.bean.Threshold;

public interface ThresholdService {
    public Threshold getThresholdByDomain(int target_domain, String norm_code);

    public void insertThreshold(Threshold threshold);

    public void updateThreshold(Threshold threshold,int id);

    public void deleteThreshold(int id);
}
