package com.springboot.environment.repositoiry.repositoiryImpl;

import com.springboot.environment.bean.HData;
import com.springboot.environment.repositoiry.HDataRepositority;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class HDataRepositoiryImpl implements HDataRepositority {

    private static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat MM = new SimpleDateFormat("MM");
    private static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat YYYYMM = new SimpleDateFormat("yyyyMM");
    private static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    EntityManager entityManager;

    @Override
    public List<HData> getNormHdataByStationIdAndTime(String stationId, String startTime, String endTime, String normCode) {
        //得到要查询的表名
        List<String> tableNameList = getTableNameList(startTime, endTime);
        if (tableNameList == null || tableNameList.size() == 0){
            return null;
        }
        System.out.println("表名" + tableNameList.toString());
        List<HData> dataList = new ArrayList<>();
        //开始和结束时间在同一分表中
        if (tableNameList.size() == 1) {
            System.out.println("开始时间 : " + startTime + " 结束时间 : " + endTime);
            //可能会有空指针异常
            return getHData(stationId, tableNameList.get(0), startTime, endTime, normCode);
        }
        else {
            for (int i = 0; i < tableNameList.size(); i++) {
                //开始时间一定是给定的时间
                if (i == 0){
                    String queryEndTime = getTableEndTime(tableNameList.get(i));
                    System.out.println("开始时间 : " + startTime + " 结束时间 : " + queryEndTime);
                    List<HData> result = getHData(stationId, tableNameList.get(i), startTime, queryEndTime, normCode);
                    if (!StringUtil.isNullOrEmpty(result)){
                        dataList.addAll(result);
                    }
                }
                //结束时间一定是最后表的结束时间
                else if (i == tableNameList.size() - 1){
                    String queryStartTime = getTableStartTime(tableNameList.get(i));
                    System.out.println("开始时间 : " + queryStartTime + " 结束时间 : " + endTime);
                    List<HData> result = getHData(stationId, tableNameList.get(i), queryStartTime, endTime, normCode);
                    if (!StringUtil.isNullOrEmpty(result)){
                        dataList.addAll(result);
                    }
                }
                else {
                    String queryStartTime = getTableStartTime(tableNameList.get(i));
                    String queryEndTime = getTableEndTime(tableNameList.get(i));
                    System.out.println("开始时间 : " + queryStartTime + " 结束时间 : " + queryEndTime);
                    List<HData> result = getHData(stationId, tableNameList.get(i), queryStartTime, queryEndTime, normCode);
                    if (!StringUtil.isNullOrEmpty(result)){
                        dataList.addAll(result);
                    }
                }
            }
        }
        System.out.println("查询的结果集长度" + dataList.size());
        return dataList;
    }

    @Override
    public List<HData> queryHdataByStationIdAndTime(String stationId, String startTime, String endTime) {

        return getNormHdataByStationIdAndTime(stationId, startTime, endTime, "");
    }
    @Override
    public List<HData> getByStationAndDate(String station_id, String date) {
        String dayStartTime = getDayBeginTime(date);
        String dayEndTime = getDayEndTime(date);
        return getNormHdataByStationIdAndTime(station_id, dayStartTime, dayEndTime, "");
    }

    private List<HData> getHData(String stationId, String tableName, String startTime, String endTime, String normCode) {

        List<HData> dataList = null;
        Query query = null;
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName + "\'";
        System.out.println(sql);
        List<String> nameResult = entityManager.createNativeQuery(sql).getResultList();
        if (StringUtil.isNullOrEmpty(nameResult)){
            /**
             * 可能分表线程没有启动，数据还存在于主表
             * 或者分表操作异常，数据存留在主表
             */
            if (normCode == null || normCode.length() == 0){
                sql = "select * from hdata m where m.station_id = ? and m.data_time between ? and ?";
                query = entityManager.createNativeQuery(sql, HData.class);
                query.setParameter(1, stationId);
                query.setParameter(2, startTime);
                query.setParameter(3, endTime);
                dataList = query.getResultList();
                if (StringUtil.isNullOrEmpty(dataList)){
                    return null;
                }
                return dataList;
            }
            else {
                sql = "select * from hdata m where m.station_id = ? and m.data_time between ? and ? and m.norm_code = ?";
                System.out.println(sql);
                query = entityManager.createNativeQuery(sql, HData.class);
                query.setParameter(1, stationId);
                query.setParameter(2, startTime);
                query.setParameter(3, endTime);
                query.setParameter(4, normCode);
                dataList = query.getResultList();
                if (StringUtil.isNullOrEmpty(dataList)){
                    return null;
                }
                return dataList;
            }
        }
        //查询所有指标数据
        if (normCode == null || normCode.length() == 0){
            sql = "select * from " + tableName + " m where m.station_id = ? and m.data_time between ? and ?";
            System.out.println(sql);
            query = entityManager.createNativeQuery(sql, HData.class);
            query.setParameter(1, stationId);
            query.setParameter(2, startTime);
            query.setParameter(3, endTime);
            dataList = query.getResultList();
        }
        else {
            sql = "select * from " + tableName + " m where m.station_id = ? and m.data_time between ? and ? and m.norm_code = ?";
            System.out.println(sql);
            query = entityManager.createNativeQuery(sql, HData.class);
            query.setParameter(1, stationId);
            query.setParameter(2, startTime);
            query.setParameter(3, endTime);
            query.setParameter(4, normCode);
            dataList = query.getResultList();
        }
        return dataList;
    }

    /*
    日期格式需要加入HH:mm
     */
    private static List<String> getTableNameList(String startTime, String endTime){
        List<String> tableName = new ArrayList<>();
        try {
            Date startDate = YYYY_MM_DD_HH_MM.parse(startTime);
            Date endDate = YYYY_MM_DD_HH_MM.parse(endTime);
            Date currDate = new Date();
            endDate = endDate.getTime() > currDate.getTime() ? currDate : endDate;
            //格式化开始结束的年份和月份
            String startYear = YYYY.format(startDate);
            String endYear = YYYY.format(endDate);
            String startMonth = MM.format(startDate);
            String endMonth = MM.format(endDate);
            int yearOffset = Integer.parseInt(endYear) - Integer.parseInt(startYear);
            int monthOffset = Integer.parseInt(endMonth) + 12 * yearOffset - Integer.parseInt(startMonth);
            //按顺构造表名
            for (int i = 0; i <= monthOffset; i++) {
                int year = (Integer.parseInt(startMonth) + i) / 12;
                int month = (Integer.parseInt(startMonth) + i) % 12;
                if (month == 0){
                    tableName.add("hdata_" + (Integer.parseInt(startYear) + year - 1) + "12");
                }
                else {
                    if (month < 10){
                        tableName.add("hdata_" + (Integer.parseInt(startYear) + year) + "0" + month);
                    }
                    else {
                        tableName.add("hdata_" + (Integer.parseInt(startYear) + year) + month);
                    }
                }
            }
            if (tableName.get(tableName.size() - 1).equals("hdata_" + DateUtil.getYearAndMonth(currDate))){
                tableName.set(tableName.size() - 1, "hdata");
            }
            return tableName;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTableStartTime(String tableName){
        if (tableName == "hdata"){
            return new SimpleDateFormat("yyyy-MM-01 00:00").format(new Date());
        }
        else {
            String yearMonth = tableName.split("_")[1];
            Calendar calendar = Calendar.getInstance();
            try {
                Date date = YYYYMM.parse(yearMonth);
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                return YYYY_MM_DD_HH_MM.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getTableEndTime(String tableName){
        if (tableName == "hdata"){
            Date currDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            return YYYY_MM_DD_HH_MM.format(calendar.getTime());
        }
        else {
            String yearMonth = tableName.split("_")[1];
            Calendar calendar = Calendar.getInstance();
            try {
                Date date = YYYYMM.parse(yearMonth);
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                return YYYY_MM_DD_HH_MM.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getDayBeginTime(String dateTime) {
        try {
            Date date = YYYY_MM_DD.parse(dateTime);
            return new SimpleDateFormat("yyyy-MM-dd 00:00").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDayEndTime(String dateTime) {
        try {
            Date date = YYYY_MM_DD.parse(dateTime);
            return new SimpleDateFormat("yyyy-MM-dd 23:59").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
