package com.springboot.environment.repositoiry.repositoiryImpl;

import com.springboot.environment.bean.M5Data;
import com.springboot.environment.repositoiry.M5DataRepositoiry;
import com.springboot.environment.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
@Transactional
public class M5DataRepositoiryImpl implements M5DataRepositoiry {

    private static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat YYYY_MM_DD_HH = new SimpleDateFormat("yyyy-MM-dd HH");

    @Autowired
    EntityManager entityManager;
    @Override
    public List<M5Data> queryMdataByStationIdAndTime(String stationId, String startTime, String endTime) {
        String tableName = getTableName(startTime);

        List<M5Data> dataList = null;
        Query query = null;
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name = \'" + tableName + "\'";
        System.out.println(sql);
        List<String> nameResult = entityManager.createNativeQuery(sql).getResultList();
        //可能分表线程还没有完成操作
        if (nameResult == null || nameResult.size() == 0){
            sql = "select * from m5data m where m.station_id = ? and m.data_time between ? and ?";
            //一定要设置Class接收类型
            query = entityManager.createNativeQuery(sql, M5Data.class);
            query.setParameter(1, stationId);
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
        query = entityManager.createNativeQuery(sql, M5Data.class);
        query.setParameter(1, stationId);
        query.setParameter(2, startTime);
        query.setParameter(3, endTime);
        dataList = query.getResultList();
        return dataList;
    }

    @Override
    public List<M5Data> getByStationAndHour(String station_id, String time1) {
        String startTime = getStartTime(time1);
        String endTime = getEndTime(time1);
         return queryMdataByStationIdAndTime(station_id, startTime, endTime);
    }

    private String getTableName(String dateTime) {
        try {
            Date date = YYYY_MM_DD_HH_MM.parse(dateTime);
            String monOfDate = DateUtil.getThisWeekMon(date);
            String currMon = DateUtil.getThisWeekMon(new Date());
            if (!monOfDate.equals(currMon)){
                return "m5data_" + monOfDate;
            }
            else {
                return "m5data";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

}
