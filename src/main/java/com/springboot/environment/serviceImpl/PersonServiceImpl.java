package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Person;
import com.springboot.environment.dao.PersonDao;
import com.springboot.environment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    public List<Person> findAll(){
        return personDao.findAll();
    }
}
