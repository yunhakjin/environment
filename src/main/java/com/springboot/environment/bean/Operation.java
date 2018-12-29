package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "operation")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Operation {
    @Id
    @Column(name = "operation_id")
    private String operation_id;

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperation_id() {
        return operation_id;
    }

    @Column(name = "operation_name")
    private String operation_name;

    public void setOperation_name(String operation_name) {
        this.operation_name = operation_name;
    }

    public String getOperation_name() {
        return operation_name;
    }

    @Column(name = "operation_tel")
    private String operation_tel;

    public void setOperation_tel(String operation_tel) {
        this.operation_tel = operation_tel;
    }

    public String getOperation_tel() {
        return operation_tel;
    }

    @Column(name = "operation_relate")
    private String operation_relate;

    public void setOperation_relate(String operation_relate) {
        this.operation_relate = operation_relate;
    }

    public String getOperation_relate() {
        return operation_relate;
    }

}
