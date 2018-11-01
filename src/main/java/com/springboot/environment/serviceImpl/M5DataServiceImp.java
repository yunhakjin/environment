package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.bean.M5Data;
import com.springboot.environment.bean.MData;
import com.springboot.environment.dao.M5DataDao;
import com.springboot.environment.service.M5DataService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.NormConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class M5DataServiceImp implements M5DataService {
    @Autowired
    private M5DataDao m5DataDao;

    @Override
    public List<M5Data> getAll() {
        return m5DataDao.findAll();
    }

    @Override
    public Page<M5Data> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return m5DataDao.findAll(pageable);
    }

    @Override
    public List<M5Data> getM5DataByData_id(String Data_id) {
        return m5DataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<M5Data> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return m5DataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public String queryM5dataByStationIdAndDatetime(String stationId, String date) {
        try {
            String startDate = DateUtil.getDateBefore1hour(date);
            String endDate = date;

            List<M5Data> m5Datas = m5DataDao.queryMdataByStationIdAndTime(stationId, startDate, endDate);

            JSONArray m5dataArray = new JSONArray();
            JSONObject m5dataJSON = new JSONObject();
            JSONObject dataJSON = new JSONObject();

            //如果指定时间内有数据
            if (m5Datas.size() > 0 ){
                Map<Date, List<M5Data>> map = Maps.newTreeMap();
                for (M5Data m5Data : m5Datas) {
                    if (map.containsKey(m5Data.getData_time())) {
                        map.get(m5Data.getData_time()).add(m5Data);
                    } else {
                        List<M5Data> m5DataList = Lists.newArrayList();
                        m5DataList.add(m5Data);
                        map.put(m5Data.getData_time(), m5DataList);
                    }
                }

                System.out.println(map.toString());

                for (List<M5Data> m5DataList : map.values()) {
                    JSONObject object = new JSONObject();
                    object.put("time", DateUtil.getHourAndMinuteAndSecond(m5DataList.get(0).getData_time()));
                    for (M5Data m5Data : m5DataList) {
                        if (NormConstant.map.containsKey(m5Data.getNorm_code())) {
                            object.put(NormConstant.map.get(m5Data.getNorm_code()), m5Data.getNorm_val());
                        }
                    }
                    m5dataArray.add(object);
                }

                //获取最新数据的val值
                String latestCal = null;
                List<M5Data> latestM5Data = ((TreeMap<Date, List<M5Data>>) map).lastEntry().getValue();
                System.out.println("最新的时间为:" + latestM5Data.get(0).getData_time());
                for (M5Data m5Data : latestM5Data){
                    if (m5Data.getNorm_code().equals("n00100")){
                        latestCal = m5Data.getNorm_val();
                    }
                }
                dataJSON.put("count", map.size());
                dataJSON.put("data", m5dataArray);
                dataJSON.put("latest_calibration_value", latestCal);
                m5dataJSON.put("siteData", dataJSON);

                System.out.println(m5dataJSON.toJSONString());
                return m5dataJSON.toJSONString();
            }

            else {
                dataJSON.put("count", 0);
                dataJSON.put("data", "");
                dataJSON.put("latest_calibration_value", "");
                m5dataJSON.put("siteData", dataJSON);

                System.out.println(m5dataJSON.toJSONString());
                return m5dataJSON.toJSONString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}

