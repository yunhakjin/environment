package com.springboot.environment.Enum;

import com.google.common.collect.Maps;

import java.util.Map;

public enum NormEnum {
    ;


    private String code;
    private String name;

    NormEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<String, NormEnum> map = Maps.newHashMap();

    static{
        for (NormEnum normEnum : NormEnum.values()){
            map.put(normEnum.getCode(), normEnum);
        }
    }



}
