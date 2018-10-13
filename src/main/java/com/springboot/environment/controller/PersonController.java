package com.springboot.environment.controller;

import com.springboot.environment.bean.Person;
import com.springboot.environment.dao.PersonDao;
import com.springboot.environment.service.PersonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("swaggerTestController相关api")
public class PersonController {
//    @RequestMapping("/hello")
//    public String index(){
//        return "Hello World";
//    }
    @Autowired
    private PersonService personService;


    @ApiOperation(value="返回所有用户的信息",notes = "所有用户的信息")
    @GetMapping("/list")
    public List<Person> findAll(){
        return personService.findAll();
    }

    @ApiOperation(value="返回某一个用户",notes = "某个用户的信息")
    @GetMapping("/getone")
    public Person findOne(){
        return personService.getOne(1);
    }

}
