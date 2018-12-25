package com.springboot.environment.service;

import com.springboot.environment.bean.Operation;

import java.util.List;

public interface OperationService {
    public List<Operation> getAllOperation();
    public void insertOperation(Operation operation);
    public void deleteOperation(String operation_id);
    public void updateOperation(Operation operation,String operation_id);
    public List<Operation> getOneOperation(String operation_id);
    public List<Operation> getOperationLike(String target);
}
