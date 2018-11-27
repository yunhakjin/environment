package com.springboot.environment.service;

import com.springboot.environment.bean.Norm;

import java.util.List;

/*指标的service
* getAll: 返回所有的指标
* getOne: 根据指标的ID查询指标
* addOne: 新增指标
* delOne: 删除指标
* updateOne: 修改指标
* getAllByOverflag:拿到所有报警指标*/
public interface NormService {

    public List<Norm> getAll();
    public void addOne(Norm norm);
    public void delOne(String norm_id_code);
    public void updateOne(String norm_id_code,Norm norm);
    public Norm getOne(String norm_id_code);
    public List<Norm> getAllByMflag();
    public List<Norm> getAllByM5flag();
    public List<Norm> getAllByHflag();
    public List<Norm> getAllByDflag();
    public List<Norm> getAllByMonthflag();
    public List<Norm> getAllByOverflag();
}
