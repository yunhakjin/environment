/*
package com.springboot.environment;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.MData;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnvironmentApplication.class)
public class EnvironmentApplicationTests {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MDataDao mDataDao;

    @Autowired
    StationDao stationDao;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testRedis(){

        ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
        //设置redis的过期时间
        keyValue.set("test", "我能输出就表示测试成功");
        redisTemplate.expire("test", 120, TimeUnit.SECONDS);
        String key = keyValue.get("test");
        System.out.println(key);

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        ops.put("stationId", "300010", "stationData");
        ops.put("stationId", "300011", "300011stationData");
        redisTemplate.expire("stationId", 120, TimeUnit.SECONDS);
        redisTemplate.expire("300010", 60, TimeUnit.SECONDS);
        System.out.println(ops.entries("stationId").toString());

        */
/**
         * 测试有序集合
         *//*

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTimeStamp = new Date().getTime();
        System.out.println("当前系统时间 = " +nowTimeStamp +  "     " + dateFormat.format(nowTimeStamp));

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add("300010","300010的当前的数据", 1542367018678.0);
        System.out.println("一小时之前的时间" + dateFormat.format(1542367018678.0 - 1000 * 60 * 60));
        zset.add("300010", "300010的一分钟之前的数据", 1542367018678.0 - 1000 * 60);
        System.out.println("时间戳的初始时间" + 0L);
        zset.add("300010", "系统初始时间的数据", 0L);

        System.out.println("指定时间戳内的所有数据" + zset.reverseRangeByScore("300010", nowTimeStamp - 1000 * 60 * 60, nowTimeStamp));
        System.out.println("当前时间" + nowTimeStamp);
        System.out.println("当前一小时之前的时间" + (nowTimeStamp - 1000 * 60 * 60));

        redisTemplate.expire("300010", 120, TimeUnit.SECONDS);


    }



    @Test
    public void testTImeStamp(){

        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        Set<String> maxScoreData = zset.reverseRange("31010702330052", 0,1);
        String data = (String) maxScoreData.toArray()[1];
        long time = (long)JSONObject.parseObject(data).get("data_time");
        System.out.println(DateUtil.getDateStr(new Date(time)));
    }


   @Test
    public void testRedisCount(){
        HashOperations<String, String, String> mdataCount = redisTemplate.opsForHash();
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        List<Station> stations = stationDao.findAll();

        for (Station station : stations){
            String stationId = station.getStationCode();
            Long count = zSet.zCard(stationId);
            System.out.println("站点" + stationId + "当前redis的数据量是" + count);
            //如果判断存在这个有序集合
            if (count != 0){
                mdataCount.put("count", stationId, String.valueOf(count));
            }
            else {
                mdataCount.put("count", stationId,  String.valueOf(0));
            }
        }

   }

   @Test
   @Transactional

   public void testCreateTable(){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
       Date date = new Date(1543291200000L);
       StringBuilder stringBuilder = new StringBuilder("mdata_");
       stringBuilder.append(sdf.format(date));
       String sql = "create table if not exists " + stringBuilder.toString() + " like mdata";

       entityManager.createNativeQuery(sql).executeUpdate();
       entityManager.flush();
       System.out.println(stringBuilder.toString());
       System.out.println("分表成功");

       String startTime = DateUtil.getDayBeforeTodayStartTime(new Date(1543291200000L));
       String endTime = DateUtil.getDayBeforeTodayEndTime(new Date(1543291200000L));


       String readWriteSql = "insert into " + stringBuilder + " select  * from mdata where data_time between \'" + startTime + "\' and \'" + endTime + "\'";
       System.out.println(readWriteSql);
       int result = entityManager.createNativeQuery(readWriteSql).executeUpdate();
       System.out.println("数据成功" + result);
   }

   @Test
   @Transactional
   @Modifying
   public void testInsert(){

        String sql = "create table testTable like mdata";
        int result = entityManager.createNativeQuery(sql).executeUpdate();
        System.out.println(result);

   }





}
*/
