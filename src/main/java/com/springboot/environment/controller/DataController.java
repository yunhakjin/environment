package com.springboot.environment.controller;

import com.springboot.environment.bean.DData;
import com.springboot.environment.service.DDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DDataService dDataService;

    @GetMapping("getAllDdata")
    public List<DData> getAllDdata(){
        return dDataService.getAll();
    }

    /*page:分页的起始页，设置为0；
    * size：分成的页数*/
    @GetMapping("getAllPage")
    public Page<DData> getAllPage(){
        int page=0;
        int size=40;
        Page<DData> pages=dDataService.getAllPage(page,size);
        return pages;
    }
}
