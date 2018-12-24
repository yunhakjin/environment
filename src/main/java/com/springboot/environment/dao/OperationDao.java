package com.springboot.environment.dao;

import com.springboot.environment.bean.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
public interface OperationDao extends JpaRepository<Operation,String> {
    @Query(value = "select * from operation",nativeQuery = true)
    public List<Operation> getAllOperation();

    @Transactional
    @Modifying
    @Query(value="insert into operation(operation_id,operation_name,operation_relate,operation_tel," +
            "operation_target) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    public void insertOperation(String operation_id,String operation_name,String operation_relate,
                                String operation_tel,String operation_target);

    @Transactional
    @Modifying
    @Query(value = "delete from operation where operation_id=?1",nativeQuery = true)
    public void deleteOperation(String operation_id);

    @Transactional
    @Modifying
    @Query(value = "update operation set operation_id=?1,operation_name=?2,operation_relate=?3," +
            "operation_tel=?4,operation_target=?5 where operation_id=?6",nativeQuery = true)
    public void updateOperation(String operation_id,String operation_name,String operation_relate,String operation_tel,
                                String operation_target,String target);
}
