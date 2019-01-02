package com.springboot.environment.task;

import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class MdataTask {

    @Autowired
    MDataDao mDataDao;

    @PersistenceContext
    EntityManager entityManager;

    public static final int BEFORE_HREE_MONTH = -3;

    private static final Logger logger = LoggerFactory.getLogger(MdataTask.class);

//    @Scheduled(cron = "0 0 0 ? * ?")
    public void createNewMdataTableByDay(){

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getYesterdayString(date));
        String startTime = DateUtil.getDayBeforeTodayStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        try {
            String sql = "create table if not exists " + tableName.toString() + " like mdata";
            entityManager.createNativeQuery(sql).executeUpdate();
            System.out.println(tableName.toString());
            System.out.println("分表成功");
            String readWriteSql = "insert into " + tableName + " select  * from mdata where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
            int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
            System.out.println("数据成功" + result);
        } catch (Exception e){
            System.out.println("分表失败，请检查数据库连接");
        }

    }

//    @Scheduled(cron = "0 0 1 ? * ?")
    public void deleteMdataByDay(){
        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getYesterdayString(date));

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName.toString() + "\'";
        System.out.println(sql);
        List<String> result = entityManager.createNativeQuery(sql).getResultList();
        if (result == null || result.size() == 0 || !result.get(0).equals(tableName.toString())){
            System.out.println("分表不存在，不能执行下一步的删除操作");
            return;
        }
        sql = "select count(*) from " + tableName.toString();
        BigInteger count = (BigInteger)entityManager.createNativeQuery(sql).getSingleResult();
        if (count.equals(BigInteger.ZERO)){
            System.out.println("分表中没有数据，不能删除主表");
            return;
        }

        String startTime = DateUtil.getDayBeforeTodayStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);
        sql = "delete from " + "mdata" + " where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        System.out.println(sql);
        int deleteResult = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("删除表中的数据" + deleteResult);

    }

//    @Scheduled(cron = "0 0 0 ? * ?")
    public void deleteTableBefore90Days(){
        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getDayWithMonthOffset(date, BEFORE_HREE_MONTH));
        try{
            String sql = "drop table if exists " + tableName.toString();
            System.out.println(sql);
            entityManager.createNativeQuery(sql).executeUpdate();
            System.out.println("删除成功" + tableName.toString());
        } catch (TransactionException e){
            System.out.println("删除异常，请检查数据库连接");
        }
    }





}
