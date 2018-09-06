package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Person implements Serializable {


    private static final long serialVersionUID = -509438491019594820L;
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public void setId(Integer id){
        this.id=id;
    }
    public Integer getId(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
}
