package com.springboot.environment.task;

import com.springboot.environment.dao.M5DataDao;
import com.springboot.environment.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class M5dataTask {

    @Autowired
    M5DataDao m5DataDao;

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(M5dataTask.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * 每个星期一将m5data分表
     * 每个星期一1时30分运行定时任务，将上一个星期的数据分表， 2时30分运行删除任务， 将m5data主表中对应的上一个星期数据删除
     * 数据的范围从星期一到星期日
     * 例如在2018-12-02 00:00:00运行定时任务 数据时间：2018-11-25 00：00：00 ～ 2018-11-1 23：59：59
     * 表名：m5data_20181125，以上个星期一为日期存储
     */
    @Scheduled(cron = "0 30 1 ? * MON")
    public void createNewM5dataTableByWeek(){

        logger.info("5分钟数据分表线程启动, 时间={}", sdf.format(new Date()));
        long begintime = System.currentTimeMillis();

        StringBuilder tableName = new StringBuilder("m5data_");
        tableName.append(DateUtil.getMonDayOfLastWeek());
        String startTime = DateUtil.getMonDayOfLastWeekBeginTime();
        String endTime = DateUtil.getSunDayOfLastWeekEndTime();

        logger.info("开始创建5分钟数据分表={}", tableName);
        String sql = "create table if not exists " + tableName.toString() + " like m5data";
        entityManager.createNativeQuery(sql).executeUpdate();
        logger.info("5分钟数据分表创建成功,表名={}", tableName);

        try {
            String readWriteSql = "insert into " + tableName.toString() + " select * from m5data where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
            logger.info("开始写入5分钟数据，表名={}, 开始时间={}, 5分钟数据开始时间={}, 5分钟数据结束时间={}", tableName.toString(), sdf.format(new Date()), startTime, endTime);
            int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
            logger.info("写数据成功, 影响行数={}, 结束时间={}, 耗时={}ms", result, sdf.format(new Date()), (System.currentTimeMillis() - begintime));
        } catch (Exception e){
            logger.error("写数据失败，请检查连接和相关配置");
        }
        logger.info("5分钟数据分表线程完成, 时间={}", sdf.format(new Date()));
    }

    @Scheduled(cron = "0 30 2 ? * MON")
    public void deleteM5dataByWeek() {

        logger.info("5分钟数据删除线程启动, 时间={}", sdf.format(new Date()));
        long begintime = System.currentTimeMillis();

        StringBuilder tableName = new StringBuilder("m5data_");
        tableName.append(DateUtil.getMonDayOfLastWeek());

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName.toString() + "\'";

        List<String> result = entityManager.createNativeQuery(sql).getResultList();
        if (result == null || result.isEmpty() || !result.get(0).equals(tableName.toString())){
            logger.info("5分钟分表{}不存在", tableName.toString());
            return;
        }

        sql = "select count(*) from " + tableName.toString();
        BigInteger count = (BigInteger)entityManager.createNativeQuery(sql).getSingleResult();
        if (count.equals(BigInteger.ZERO)){
            logger.info("5分钟数据分表{}为空，不能删除", tableName.toString());
            return;
        }
        String startTime = DateUtil.getMonDayOfLastWeekBeginTime();
        String endTime = DateUtil.getSunDayOfLastWeekEndTime();
        logger.info("开始删除5分钟主表数据, 开始时间={}, 数据开始时间={}, 数据结束时间={}", sdf.format(new Date()), startTime, endTime);
        sql = "delete from " + "m5data" + " where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        int deleteResult = entityManager.createNativeQuery(sql).executeUpdate();
        logger.info("删除主表5分钟数据成功, 影响行数={}, 结束时间={}, 耗时={}ms", deleteResult, sdf.format(new Date()), System.currentTimeMillis() - begintime);
    }
}
