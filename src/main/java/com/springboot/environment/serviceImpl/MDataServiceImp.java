package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.MData;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.service.MDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MDataServiceImp implements MDataService {
    @Autowired
    private MDataDao mDataDao;

    @Override
    public List<MData> getAll() {
        return mDataDao.findAll();
    }

    @Override
    public Page<MData> getAllPage(int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.findAll(pageable);
    }

    @Override
    public List<MData> getMDataByData_id(String Data_id) {
        return mDataDao.getAllByData_id(Data_id);
    }

    @Override
    public Page<MData> getByStationAndTime(String station_id, String starttime, String endtime, int data_check, int data_status, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return mDataDao.getByStationAndTime(station_id,starttime,endtime,data_check,data_status,pageable);
    }

    @Override
    public Map getMDataByStationsID() {
        String stationsID ="{ stations:[\"31010702330055\",\"31010702330053\"]}";
        //String json="{date:\"2018-09-07\",cars:[\"movingcar06\",\"movingcar05\"]}";
        //JSONArray station_json=JSONArray.fromObject(stationsID);
        //Map gatherQuery=JSONObject.parseObject(json);
        //JSONObject ao=jsarr.getJSONObject(0);
        //String stations=(String)gatherQuery.get("date");
        JSONObject jso= JSON.parseObject(stationsID);
        JSONArray stations=jso.getJSONArray("stations");
        System.out.println(stations.size());
        System.out.println(stations.get(0));
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("data",null);
        for(int i =0;i < stations.size(); i++ ){
       /* //"station_id":"1","station_name":"华师大", "L50": 107.7,
        List<MData> mDatas=dDataDao.getLatestMDataStations(stations.get(i));
        String station_id=stations.get(i)+"";
        Station station=stationDao.findByStationId(station_id);
        String station_name=station.getStationName();
        for (MData mdata:mDatas) {
            //mdata.getNorm_code()
        }*/
        }
        return resultMap;
    }
}
