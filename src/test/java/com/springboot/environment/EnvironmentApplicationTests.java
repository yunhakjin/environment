package com.springboot.environment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnvironmentApplicationTests {

//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//
//    @Test
//    public void testRedis(){
//
//        ValueOperations<String, String> keyValue = redisTemplate.opsForValue();
//        keyValue.set("这是一个测试", "我能输出就表示测试成功");
//        String key = keyValue.get("这是一个测试");
//        System.out.println(key);
//    }

}
