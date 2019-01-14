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
import java.text.SimpleDateFormat;
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
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


    /**
     * 分钟数据的分表任务，每天1时定时，将前一天的数据分表
     * 表名 : mdata_yyyyMMdd 例如:mdata_20181231存储当天的所有数据
     * 分钟数据删除任务，每天2时定时，将前一天Mdata中的数据删除
     * 因为分钟数据只保留三个月，所以有分表删除任务
     * 分表删除任务， 每天0时运行，将改天前3个月的分表数据删除。
     * 例如：20181231 0时运行，将改天前3个月的分表删除
     */
    @Scheduled(cron = "0 0 1 ? * ?")
    public void createNewMdataTableByDay(){

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getYesterdayString(date));
        String startTime = DateUtil.getDayBeforeTodayStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        try {
            logger.info("分表开始，名称={} ", tableName.toString());
            String sql = "create table if not exists " + tableName.toString() + " like mdata";
            entityManager.createNativeQuery(sql).executeUpdate();
            logger.info("分表成功,名称={}", tableName.toString());
            String readWriteSql = "insert into " + tableName + " select  * from mdata where data_time between \'" + startTime + "\' and \'" + endTime + "\'";

            logger.info("开始对 {}----{}的数据分表, 开始时间={}", sdf.format(new Date()));
            int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
            logger.info("分表结束，影响行数={}, 结束时间={}", result, sdf.format(new Date()));

        } catch (Exception e){
            logger.error("分表出现异常");
        }

    }

    @Scheduled(cron = "0 0 2 ? * ?")
    public void deleteMdataByDay(){
        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getYesterdayString(date));

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName.toString() + "\'";

        List<String> result = entityManager.createNativeQuery(sql).getResultList();
        if (result == null || result.size() == 0 || !result.get(0).equals(tableName.toString())){
            logger.info("分表{}不存在，不能执行下一步的删除操作", tableName.toString());
            return;
        }
        sql = "select count(*) from " + tableName.toString();
        BigInteger count = (BigInteger)entityManager.createNativeQuery(sql).getSingleResult();
        if (count.equals(BigInteger.ZERO)){
            logger.info("分表{}中没有数据，不能删除主表", tableName);
            return;
        }

        String startTime = DateUtil.getDayBeforeTodayStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);
        sql = "delete from " + "mdata" + " where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        int deleteResult = entityManager.createNativeQuery(sql).executeUpdate();
        logger.info("{}----{}分钟数据删除成功，影响行数={}", startTime, endTime, deleteResult);
    }

    @Scheduled(cron = "0 0 0 ? * ?")
    public void deleteTableBefore90Days(){
        Date date = new Date();
        StringBuilder tableName = new StringBuilder("mdata_");
        tableName.append(DateUtil.getDayWithMonthOffset(date, BEFORE_HREE_MONTH));
        try{
            String sql = "drop table if exists " + tableName.toString();
            System.out.println(sql);
            entityManager.createNativeQuery(sql).executeUpdate();
            logger.info("删除成功，表名={}", tableName);
        } catch (TransactionException e){
            logger.error("删除表异常，请检查连接配置等信息");
        }
    }





}
