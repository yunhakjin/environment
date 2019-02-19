package com.springboot.environment.util;

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

    public static String getDateBeforeHour(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getHourAndMinute(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getDateAfter1Hour(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date nowDate = null;
        try {
            nowDate = sdf.parse(date);
            calendar.setTime(nowDate);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return sdf.format(calendar.getTime());
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
            return new SimpleDateFormat("yyyy-MM-dd 00:00").format(nowDate);
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
            return new SimpleDateFormat("yyyy-MM-dd 23:59").format(nowDate);
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
            String firstDay = new SimpleDateFormat("yyyy-MM-01 00:00").format(nowDate);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(calendar.getTimeInMillis()));
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
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime());
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

    public static String getDayBeforeTodayStartTime(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return getThisDayStartTime(sdf.format(date));

    }

    /**
     * 得到昨天的结束时间
     * @param date
     * @return
     */
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

    /**
     * 取得上周一的时间
     * @return
     */
    public static String getMonDayOfLastWeek(){
        LocalDate today = LocalDate.now();
        LocalDate montoLastWeek = today.minusWeeks(1).with(DayOfWeek.MONDAY);
         return montoLastWeek.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 得到本周一的开始时间
     * @param date
     * @return
     */
    public static String getThisWeekMon(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        LocalDate localDate = LocalDate.parse(dateStr);
        localDate = localDate.minusWeeks(0).with(DayOfWeek.MONDAY);
        return localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 取得上周一的开始时间
     * @return
     */
    public static String getMonDayOfLastWeekBeginTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String monDayOfLastWeek = getMonDayOfLastWeek();
        try {
            Date date = sdf.parse(monDayOfLastWeek);
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSunDayOfLastWeekEndTime() {
        LocalDate today = LocalDate.now();
        LocalDate sunOfLastWeek = today.minusWeeks(1).with(DayOfWeek.SUNDAY);
        return sunOfLastWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
    }

    /**
     * 得到上个月的开始时间 2018-12-01 00:00:00
     * @param date
     * @return
     */
    public static String getStartDayBeforeOneMonth(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    public static String getHdataTableName(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 得到当前的年份和月份
     * @param date
     * @return
     */
    public static String getYearAndMonth(Date date){
        return new SimpleDateFormat("yyyyMM").format(date);
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

    /**
     * 判断给定年月是否是当前年月
     * @param nowDate
     * @param givenTime
     * @return
     */
    public static boolean isCurrYearAndMonth(Date nowDate, String givenTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        int currYear = calendar.get(Calendar.YEAR);
        //这里需要做加1处理
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        return (currYear == Integer.parseInt(givenTime.split("-")[0]) && currMonth == Integer.parseInt(givenTime.split("-")[1]));
    }

    /**
     * 获得当前的天数
     * @param nowDate
     * @return
     */
    public static int getDayNowDate(Date nowDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) {
        Date date = new Date();
//        String givenTime = "2019-01";
//        System.out.println(getDayNowDate(date));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        System.out.println(sdf.format(calendar.getTime()));
        System.out.println(getDateBeforeHour(date));

    }


}


