package com.springboot.environment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {



    public static final int MILLISECOND = 1000;
    public static final int SECOND = 60;
    public static final int MINUTE = 60;
    public static final int HOUR = 24;
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

    /**
     * 日期转化为字符串不包括秒
     */
    public static String getDateBeforeSecond(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getHourAndMinute(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getHourAndMinuteAndSecond(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获得指定时间一小时的时间,格式为yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    public static String getDateBefore1hour(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nowDate = sdf.parse(date);
        Date before1hour = new Date(nowDate.getTime() - MINUTE * SECOND * MILLISECOND);

        return sdf.format(before1hour);
    }

    /**
     * 获取但前时间所在天的起始时间
     * @param date
     * @return
     */
    public static String getThisDayStartTime(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(date);
        String dayBeginTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(nowDate);
        return dayBeginTime;
    }

    /**
     * 获取当前时间所在天的结束时间
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getThisDayEndTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = sdf.parse(date);
        String dayEndTime = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(nowDate);
        return dayEndTime;

    }

    public static void main(String[] args) throws ParseException {

        System.out.println(getDateStr(new Date()));
        System.out.println(getThisDayStartTime(getDateStr(new Date())));
    }


}


