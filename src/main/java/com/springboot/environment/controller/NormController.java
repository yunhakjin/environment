package com.springboot.environment.controller;

import com.springboot.environment.bean.Norm;
import com.springboot.environment.service.NormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value="/norm")
public class NormController {
    @Autowired
    private NormService normService;

    @RequestMapping(value = "/getall")
    public List<Norm> getAll(){
        return normService.getAll();
    }

    @RequestMapping(value = "/addone/{norm_id_code}/{norm_code}/{norm_name}/{norm_status}/{application}")
    public void addOne(@PathVariable String norm_id_code,
                       @PathVariable String norm_code,
                       @PathVariable String norm_name,
                       @PathVariable int norm_status,
                       @PathVariable String application){
        Norm norm=new Norm();
        norm.setApplication(application);
        norm.setNorm_code(norm_code);
        norm.setNorm_id_code(norm_id_code);
        norm.setNorm_name(norm_name);
        norm.setNorm_status(norm_status);
        normService.addOne(norm);
    }

    @RequestMapping(value = "/updateone/{norm_id_code}/{norm_code}/{norm_name}/{norm_status}/{application}")
    public void updateOne(@PathVariable String norm_id_code,
                       @PathVariable String norm_code,
                       @PathVariable String norm_name,
                       @PathVariable int norm_status,
                       @PathVariable String application){
        Norm norm=new Norm();
        norm.setNorm_id_code(norm_id_code);
        norm.setNorm_code(norm_code);
        norm.setNorm_name(norm_name);
        norm.setNorm_status(norm_status);
        norm.setApplication(application);
        normService.updateOne(norm);
   }


    @RequestMapping(value="/deleteone/{norm_id_code}")
    public void deleteOne(@PathVariable String norm_id_code){
        normService.delOne(norm_id_code);
    }


}
