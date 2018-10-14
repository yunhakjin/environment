package com.springboot.environment.service;

import com.springboot.environment.bean.DData;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DDataService {
    public List<DData> getAll();
    public Page<DData> getAllPage(int page,int size);
    public List<DData> getDDataByData_id(String Data_id);
    public void saveDData(DData dData);
    public void delDData(String Data_id);
}
