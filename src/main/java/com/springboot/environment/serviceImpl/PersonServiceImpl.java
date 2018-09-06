package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Person;
import com.springboot.environment.dao.PersonDao;
import com.springboot.environment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Person> findAll(){
        return personDao.findAll();
    }

    @Override
    public Person getOne(Integer id){
        Person person=(Person)redisTemplate.opsForValue().get(id);
        if(person==null){
            person=personDao.getOne(id);
            redisTemplate.opsForValue().set(id,person);
        }
        return person;
    }
}
