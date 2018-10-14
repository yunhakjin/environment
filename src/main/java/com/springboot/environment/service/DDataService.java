package com.springboot.environment.service;

import com.springboot.environment.bean.DData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DDataService {
    public List<DData> getAll();
    public Page<DData> getAllPage(int page,int size);
}
