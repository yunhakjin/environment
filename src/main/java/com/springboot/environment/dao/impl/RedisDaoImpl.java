package com.springboot.environment.dao.impl;

import com.springboot.environment.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

public class RedisDaoImpl implements RedisDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Set<String> getRedisMdataByStationIdAndTimeRange(String stationId, long startTime, long endTime) {

        Set<String> dataSet = null;
        if (startTime > endTime){
            return dataSet;
        }
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        dataSet = zset.reverseRangeByScore(stationId, startTime, endTime);
        return dataSet;
    }

    public Set<String> getMaxscoreData(String stationId){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        Set<String> dataSet = zset.reverseRange(stationId, 0, 0);
        return dataSet;
    }
}
