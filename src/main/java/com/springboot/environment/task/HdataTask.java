package com.springboot.environment.task;

import com.springboot.environment.dao.HDataDao;
import com.springboot.environment.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HdataTask {

    @Autowired
    HDataDao hDataDao;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * 小时数据分表策略，每个月做一次数据分表。
     * 表名:  hdata_201812, 以月份为标识
     * 存储数据 : 2018-12-01 00:00:00 ～ 2018-12-31 23:59:59的一个月小时数据
     * 定时时间: 凌晨3点做分表的写操作，凌晨4点做主表的删除操作
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    public void createHdataTableByMonths() {
        long beginTime = System.currentTimeMillis();

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("hdata_");
        tableName.append(DateUtil.getHdataTableName(date));

        String startTime = DateUtil.getStartDayBeforeOneMonth(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        String sql = "create table if not exists " + tableName.toString() + " like hdata";
        System.out.println(sql);
        int createResult = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("小时数据分表创建成功 " + createResult);

        sql = "insert into " + tableName.toString() + " select * from hdata where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        System.out.println(sql);
        int insertResult = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("小时数据成功 影响 " + insertResult + " 耗时 " + (System.currentTimeMillis() - beginTime));
    }

    @Scheduled(cron = "0 0 4 1 * ?")
    public void deleteHdataByMonths() {
        long beginTime = System.currentTimeMillis();

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("hdata_");
        tableName.append(DateUtil.getHdataTableName(date));

        String startTime = DateUtil.getStartDayBeforeOneMonth(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName.toString() + "\'";
        System.out.println(sql);
        List<String> result = entityManager.createNativeQuery(sql).getResultList();
        if (result == null || result.isEmpty() || !result.get(0).equals(tableName.toString())){
            System.out.println("hdata分表不存在，不能执行下一步的删除操作");
            return;
        }

        sql = "select count(*) from " + tableName.toString();
        BigInteger count = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
        if (count.equals(BigInteger.ZERO)){
            System.out.println("hdata分表中没有数据，请检查数据库");
            return;
        }
        sql = "delete from " + "hdata" + " where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        System.out.println(sql);
        int deleteResult = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("删除hdata主表数据成功 影响" + deleteResult + " 耗时" + (System.currentTimeMillis() - beginTime));

    }
}
