package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Norm;
import com.springboot.environment.dao.NormDao;
import com.springboot.environment.service.NormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
/*指标的service的实现类
 * getAll: 返回所有的指标
 * getOne: 根据指标的ID查询指标
 * addOne: 新增指标 为了灵活增加指标，预先在数据库的指标表中增加了tmpx字段，新增就是取第一个tmpx进行修改
 * delOne: 删除指标  为了灵活增加指标，预先在数据库的指标表中增加了tmpx字段，删除就是将目标指标替换成tmpx
 * updateOne: 修改指标*/
public class NormServiceImpl implements NormService {
    @Autowired
    private NormDao normDao;

    @Override
    public List<Norm> getAll(){
        return normDao.findAll();
    }

    @Override
    public void addOne(Norm norm){
        normDao.save(norm);
    }

    @Override
    public void delOne(String norm_id_code){
        normDao.deleteById(norm_id_code);
    }

    @Override
    public void updateOne(String norm_id_code,Norm norm){
        normDao.deleteById(norm_id_code);
        normDao.save(norm);
    }

    @Override
    public Norm getOne(String norm_id_code){
        return normDao.getOne(norm_id_code);
    }

    @Override
    public List<Norm> getAllByMflag() {
        return normDao.getAllByMflag();
    }

    @Override
    public List<Norm> getAllByM5flag() {
        return normDao.getAllByM5flag();
    }

    @Override
    public List<Norm> getAllByHflag() {
        return normDao.getAllByHflag();
    }

    @Override
    public List<Norm> getAllByDflag() {
        return normDao.getAllByDflag();
    }

    @Override
    public List<Norm> getAllByMonthflag() {
        return normDao.getAllByMonthflag();
    }

    @Override
    public List<Norm> getAllByOverflag() {
        return normDao.getAllByOverflag();
    }
}
