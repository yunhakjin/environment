package com.springboot.environment.service;

import com.springboot.environment.bean.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person getOne(Integer id);
}
