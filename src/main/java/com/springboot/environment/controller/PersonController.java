package com.springboot.environment.controller;

import com.springboot.environment.bean.Person;
import com.springboot.environment.dao.PersonDao;
import com.springboot.environment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
//    @RequestMapping("/hello")
//    public String index(){
//        return "Hello World";
//    }
    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public List<Person> findAll(){
        return personService.findAll();
    }

    @GetMapping("/getone")
    public Person findOne(){
        return personService.getOne(1);
    }

}
