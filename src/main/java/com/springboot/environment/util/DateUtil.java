package com.springboot.environment.util;

import org.apache.tomcat.jni.Local;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
        return sdf.format(nowDate);
    }

    public static String getTodayEndStr(Date nowDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        return sdf.format(nowDate);
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

    public static long dateToDateStamp(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datedate = sdf.parse(date);
        return datedate.getTime();
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
    public static String getDateBefore1hour(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            Date before1hour = new Date(nowDate.getTime() - MINUTE * SECOND * MILLISECOND);
            return sdf.format(before1hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到指定时间的后续指定分钟时间
     * @param date
     * @param minutes
     * @return
     * @throws ParseException
     */
    public static String getDateAfterMinutes(String date, int minutes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            Date afterMinutesDate = new Date(nowDate.getTime() + minutes * SECOND * MILLISECOND);
            return new SimpleDateFormat("HH:mm").format(afterMinutesDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String getDateAfterHour(String date, int hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.HOUR_OF_DAY, hour);
            nowDate = calendar.getTime();
            return new SimpleDateFormat("HH").format(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getHour(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    /**
     * 获取但前时间所在天的起始时间
     * @param date
     * @return
     */
    public static String getThisDayStartTime(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            String dayBeginTime = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(nowDate);
            return dayBeginTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得小时数据的偏移日期
     * @param date
     * @param offset
     * @return
     */
    public static String getHDateTimeByOffset(String date, int offset){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date currTime = sdf.parse(date);
            calendar.setTime(currTime);
            calendar.add(Calendar.DAY_OF_MONTH, offset);
            currTime = calendar.getTime();
            return sdf.format(currTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得日数据的月份偏移
     * @param date
     * @param offset
     * @return
     */
    public static String getDdateTimeByOffset(String date, int offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            Date currTime = sdf.parse(date);
            calendar.setTime(currTime);
            calendar.add(Calendar.MONTH, offset);
            currTime = calendar.getTime();
            return sdf.format(currTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间所在天的结束时间
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getThisDayEndTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            String dayEndTime = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(nowDate);
            return dayEndTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前时间的前一天
     * @param date
     * @return
     */
    public static String getYesterdayString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 得到月初的一天
     * @param date
     * @return
     */
    public static String getMonthFirstDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            String firstDay = new SimpleDateFormat("yyyy-MM-01 00:00:00").format(nowDate);
            return firstDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到月初前一天的6点时刻
     * @param date
     * @return
     */
    public static String getLastMonthSixClock(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        try {
            Date nowDate = sdf.parse(date);
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.add(Calendar.HOUR_OF_DAY, 6);
            nowDate = calendar.getTime();
            return sdf.format(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得时间戳
     * @param date
     * @return
     */
    public static long getTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time.getTime();
    }

    public static String getTimeUntilMM(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date time = sdf.parse(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到月末的结束时间
     * @param date
     * @return
     */
    public static String getMonthEndDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date nowDay = null;
        try {
            nowDay = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDay);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(calendar.getTimeInMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 得到月末日期的6点时刻
     * @param date
     * @return
     */
    public static String getMonthDayEndTimeSixClock(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当月的天数
     * @param date
     * @return
     */
    public static int getDayNumOfMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            calendar.setTime(nowDate);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getYearMonthDay(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    public static String getDayBeforeTodayStartTime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return getThisDayStartTime(sdf.format(date));

    }

    public static String getDayBeforeTodayEndTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return getThisDayEndTime(sdf.format(date));

    }

    public static String getDayWithMonthOffset(Date date, int offset){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, offset);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getDayBeforeOneWeekStartTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getSunDayOfLastWeek(){
        LocalDate today = LocalDate.now();
        LocalDate sundaytoLastWeek = today.minusWeeks(1).with(DayOfWeek.SUNDAY);
         return sundaytoLastWeek.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getStartDayBefore3Month(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getHdataTableName(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 得到日期中的天
     * @param date
     * @return
     */
    public static int getDayOfThisDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到日期中的小时
     * @param date
     * @return
     */
    public static int gethourOfThisDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得加一天后的日期
     * @param date
     * @return
     */
    public static int getDayAfterThisDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(getMonthDayEndTimeSixClock("2018-2"));
//        System.out.println(getDayNumOfMonth("2018-11"));
//        String s = "2018-10-10";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sdf.parse(s);
//        System.out.println(getThisDateOfDay(date));
        System.out.println(getDayAfterThisDay(new Date(getTime("2018-9-30 22:00:00"))));
    }


}


