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
        for(int i=0;i<20;i++){
            if(normDao.findById("tmp"+i).isPresent()){
                normDao.deleteById("tmp"+i);
                normDao.save(norm);
                return;
            }
        }
        normDao.save(norm);
    }

    @Override
    public void delOne(String norm_id_code){
        for(int i=0;i<20;i++){
            if(normDao.findById("tmp"+i).isPresent()){
            }
            else{
                normDao.deleteById(norm_id_code);
                Norm norm=new Norm();
                String tmpStr="tmp"+(i);
                norm.setNorm_id_code(tmpStr);
                norm.setNorm_code(tmpStr);
                norm.setNorm_name(tmpStr);
                norm.setNorm_status(0);
                norm.setApplication("");
                normDao.save(norm);
                return;
            }
        }
    }

    @Override
    public void updateOne(Norm norm){
        normDao.save(norm);
    }

    @Override
    public Norm getOne(String norm_id_code){
        return normDao.getOne(norm_id_code);
    }
}
