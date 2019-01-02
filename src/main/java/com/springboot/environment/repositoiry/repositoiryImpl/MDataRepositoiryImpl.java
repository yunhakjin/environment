package com.springboot.environment.repositoiry.repositoiryImpl;

import com.springboot.environment.bean.MData;
import com.springboot.environment.repositoiry.MDataRepositoiry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MDataRepositoiryImpl implements MDataRepositoiry {

    private static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat YYYY_MM_DD_HH = new SimpleDateFormat("yyyy-MM-dd HH");
    private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MData> queryMdataByStationIdAndTime(String statonId, String startTime, String endTime) {
        String tableName = getTableNameByyyyyMMddHHmmss(startTime);

        List<MData> dataList = null;
        Query query = null;
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName + "\'";
        System.out.println(sql);
        List<String> nameResult = entityManager.createNativeQuery(sql).getResultList();
        //可能分表线程还没有完成操作
        if (nameResult == null || nameResult.size() == 0){
            sql = "select * from mdata m where m.station_id = ? and m.data_time between ? and ?";
            //一定要设置Class接收类型
            query = entityManager.createNativeQuery(sql, MData.class);
            query.setParameter(1, statonId);
            query.setParameter(2, startTime);
            query.setParameter(3, endTime);
            dataList = query.getResultList();
            if (dataList == null || dataList.size() == 0){
                return null;
            }
            return dataList;
        }
        sql = "select * from " + tableName + "  m where m.station_id = ? and m.data_time between ? and ?";
        System.out.println(sql);
        query = entityManager.createNativeQuery(sql, MData.class);
        query.setParameter(1, statonId);
        query.setParameter(2, startTime);
        query.setParameter(3, endTime);
        dataList = query.getResultList();
        return dataList;
    }

    @Override
    public List<MData> getByStationAndHour(String station_id, String date) {
        String startTime = getStartTime(date);
        String endTime = getEndTime(date);
        return queryMdataByStationIdAndTime(station_id, startTime, endTime);
    }

    private static String getStartTime(String dateTime) {
        try {
            Date date = YYYY_MM_DD_HH.parse(dateTime);
            return YYYY_MM_DD_HH_MM.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEndTime(String dateTime){
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = YYYY_MM_DD_HH.parse(dateTime);
            calendar.setTime(date);
            calendar.set(Calendar.MINUTE, 59);
            return YYYY_MM_DD_HH_MM.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTableNameByyyyyMMddHHmmss(String dateTime) {
        Date nowDate = new Date();
        Date begin = getDayBeginTime(nowDate);
        Date end = getDayEndTime(nowDate);
        try {
            Date date = YYYY_MM_DD_HH_MM.parse(dateTime);
            //如果是当天数据，那么直接返回原表
            if (date.getTime() >= begin.getTime() && date.getTime() <= end.getTime()){
                return "mdata";
            }
            //否则返回分表名
            else {
                return "mdata_" + YYYYMMDD.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Date getDayBeginTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
