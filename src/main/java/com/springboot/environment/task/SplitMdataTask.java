package com.springboot.environment.task;

import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Transactional
public class SplitMdataTask {

    @Autowired
    MDataDao mDataDao;

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(SplitMdataTask.class);

    @Scheduled(cron = "0 01 12 ? * ?")
    public void createNewMdataTableByDay(){

        Date date = new Date();
        StringBuilder stringBuilder = new StringBuilder("mdata_");
        stringBuilder.append(DateUtil.getYesterdayString(date));
        String sql = "create table if not exists " + stringBuilder.toString() + " like mdata";
        entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println(stringBuilder.toString());
        System.out.println("分表成功");

        String startTime = DateUtil.getDayBeforeTodayStartTime(date);
        String endTime = DateUtil.getDayBeforeTodayEndTime(date);

        String readWriteSql =  "insert into " + stringBuilder + " select  * from mdata where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
        int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
        System.out.println("数据成功" + result);

    }





}
