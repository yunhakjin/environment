package com.springboot.environment.Enum;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public enum  DataTypeEnum {

    MDATA(1, "实时数据"),
    M5DATA(2, "5分钟数据"),
    HDATA(3, "小时数据"),
    DDATA(4, "日数据");


    private int code;
    private String name;

    public static Map<Integer, DataTypeEnum> map = new HashMap<>();

    static {
        for (DataTypeEnum dataTypeEnum : DataTypeEnum.values()){
            map.put(dataTypeEnum.getCode(), dataTypeEnum);
        }
    }

    public static DataTypeEnum toEnum(Integer code){
        return map.get(code);
    }

    DataTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
