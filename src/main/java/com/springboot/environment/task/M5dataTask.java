package com.springboot.environment.task;

import com.springboot.environment.dao.M5DataDao;
import com.springboot.environment.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class M5dataTask {

    @Autowired
    M5DataDao m5DataDao;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * 每个星期日将m5data分表
     * 每个星期日0时运行定时任务，将上一个星期的数据分表
     * 数据的范围从星期日到星期六
     * 例如在2018-12-02 00:00:00运行定时任务 数据时间：2018-11-25 00：00：00 ～ 2018-11-1 23：59：59
     * 表名：m5data_20181125，以上个星期日为日期存储
     */
//    @Scheduled(cron = "0 30 1 ? * SUN")
    public void createNewM5dataTableByWeek(){
        long begintime = System.currentTimeMillis();

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("m5data_");
        tableName.append(DateUtil.getSunDayOfLastWeek());
        System.out.println("表名为" + tableName);
        String startTime = DateUtil.getDayBeforeOneWeekStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        String sql = "create table if not exists " + tableName.toString() + " like m5data";
        entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("表创建成功" + new Date());

        String readWriteSql = "insert into " + tableName.toString() + " select * from m5data where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        System.out.println(readWriteSql);
        int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
        System.out.println("数据成功 影响 " + result + " 耗时 " + (System.currentTimeMillis() - begintime));
    }

//    @Scheduled(cron = "0 30 2 ? * SUN")
    public void deleteM5dataByWeek() {
        long begintime = System.currentTimeMillis();

        Date date = new Date();
        StringBuilder tableName = new StringBuilder("m5data_");
        tableName.append(DateUtil.getSunDayOfLastWeek());
        System.out.println("表名为" + tableName);

        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName.toString() + "\'";
        System.out.println(sql);
        List<String> result = entityManager.createNativeQuery(sql).getResultList();
        if (result == null || result.isEmpty() || !result.get(0).equals(tableName.toString())){
            System.out.println("m5data分表不存在，不能执行下一步的删除操作");
            return;
        }

        sql = "select count(*) from " + tableName.toString();
        BigInteger count = (BigInteger)entityManager.createNativeQuery(sql).getSingleResult();
        if (count.equals(BigInteger.ZERO)){
            System.out.println("m5data分表中没有数据，请检查数据库");
            return;
        }
        String startTime = DateUtil.getDayBeforeOneWeekStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);
        sql = "delete from " + "m5data" + " where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        System.out.println(sql);
        int deleteResult = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println("删除分表中的数据成功 " + deleteResult + " 耗时 " + (System.currentTimeMillis() - begintime));
    }
}
