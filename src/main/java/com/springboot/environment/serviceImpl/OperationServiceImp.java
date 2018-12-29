package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Operation;
import com.springboot.environment.dao.OperationDao;
import com.springboot.environment.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationServiceImp implements OperationService {
    @Autowired
    private OperationDao operationDao;

    @Override
    public List<Operation> getAllOperation(){
        return operationDao.getAllOperation();
    }

    @Override
    public void insertOperation(Operation operation){
        String operation_id=operation.getOperation_id();
        String operation_name=operation.getOperation_name();
        String operation_relate=operation.getOperation_relate();
        String operation_tel=operation.getOperation_tel();
        operationDao.insertOperation(operation_id,operation_name,operation_relate,operation_tel);
    }

    @Override
    public void deleteOperation(String operation_id){
        operationDao.deleteOperation(operation_id);
    }

    @Override
    public void updateOperation(Operation operation,String target){
        String operation_id=operation.getOperation_id();
        String operation_name=operation.getOperation_name();
        String operation_relate=operation.getOperation_relate();
        String operation_tel=operation.getOperation_tel();
        operationDao.updateOperation(operation_id,operation_name,operation_relate,operation_tel,target);
    }

    @Override
    public List<Operation> getOneOperation(String operation_id){
        return operationDao.getOneOperation(operation_id);
    }

    @Override
    public List<Operation> getOperationLike(String target){
        return operationDao.getOperationLike(target);
    }
}
