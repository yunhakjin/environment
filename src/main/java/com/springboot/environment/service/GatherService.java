package com.springboot.environment.service;

import com.springboot.environment.bean.Gather;

import java.util.List;

public interface GatherService {
    public List<Gather> getAllGather();
    public Gather getOneGather(String gather_id);
}
