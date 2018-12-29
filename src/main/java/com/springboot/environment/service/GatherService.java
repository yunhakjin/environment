package com.springboot.environment.service;

import com.springboot.environment.bean.Gather;

import java.util.List;

public interface GatherService {
    public List<Gather> getAllGather();
    public Gather getOneGather(String gather_id);
    public void insertGather(Gather gather,String setupdate);
    public void deleteGather(String gather_id);
    public void updateGather(Gather gather,String setupdate,String target);
    public void updateGatherOperation(String operation_id,String gather_id);
    public List<Gather> getGatherByOperation_id(String operation_id);
    List<Gather> findByOperationId(String operatationId);
}
