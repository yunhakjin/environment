package com.springboot.environment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


    /**
     * 获取当天开始时间的字符串
     * @param nowDate
     * @return String
     */
    public static String getTodayStr(Date nowDate){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String nowDateStr = sdf.format(nowDate);
        return nowDateStr;
    }


    /**
     * 日期格式转化为字符串
     * @param date
     * @return
     */
    public static String getDateStr(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}


