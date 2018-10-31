package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.DData;
import com.springboot.environment.bean.HData;
import com.springboot.environment.bean.MData;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.DDataDao;
import com.springboot.environment.dao.HDataDao;
import com.springboot.environment.dao.MDataDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao stationDao;

    @Autowired
    MDataDao mDataDao;

    @Autowired
    HDataDao hDataDao;

    @Autowired
    DDataDao dDataDao;


    @Override
    public List<Station> findALl() {
        return stationDao.findAll();
    }

    @Override
    public List<Station> queryStationsByCountryCon(int isCountryCon) {

        if (isCountryCon != StationConstant.STATION_IS_COUNTRY_CON && isCountryCon != StationConstant.STATION_ISNOT_COUNTRY_CON){
           throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByCountryCon(isCountryCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByStatus(int stationStatus) {

        if (stationStatus != StationConstant.STATION_RUN && stationStatus != StationConstant.STATION_DISABLE){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByStationStatus(stationStatus);
        return stations;
    }

    @Override
    public List<Station> queryStationsByOnlineFlag(int onlineFlag) {

        if (onlineFlag != StationConstant.STATION_ONLINE && onlineFlag != StationConstant.STATION_OFFLINE){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByOnlineFlag(onlineFlag);
        return stations;
    }

    @Override
    public List<Station> queryStationsByNameLike(String stationName) {
        List<Station> stations = stationDao.findByStationNameLike(stationName);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDistrict(String district) {
        List<Station> stations = stationDao.findByDistrict(district);
        return stations;
    }

    @Override
    public List<Station> queryStationsByStreet(String street) {
        List<Station> stations = stationDao.findByStreetLike(street);
        return stations;
    }

    @Override
    public List<Station> queryStationsByCityCon(int isCityCon) {

        if (isCityCon != StationConstant.STATION_IS_CITY_CON && isCityCon != StationConstant.STATION_ISNOT_CITY_CON){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByCityCon(isCityCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDomainCon(int isDomainCon) {
        if (isDomainCon != StationConstant.STATION_IS_DOMAIN_CON && isDomainCon != StationConstant.STATION_ISNOT_DOMAIN_CON){
            throw new RuntimeException();
        }

        List<Station> stations = stationDao.findByDomainCon(isDomainCon);
        return stations;
    }

    @Override
    public List<Station> queryStationsByArea(int area) {
        List<Station> stations = stationDao.findByArea(area);
        return stations;
    }

    @Override
    public List<Station> queryStationsByDomain(int domain) {
        List<Station> stations = stationDao.findByDomain(domain);
        return stations;
    }

    @Transactional
    @Override
    public String addStation(String stationId, String stationCode, String stationName, String stationStatus, String application, String onlineFlag, String stationIdDZ, String protocol, String protocolName, String position, String street, String district, String range, String countryCon, String cityCon, String domainCon, String area, String domain) {
        Station station = new Station();
        station.setStationId(stationId);
        station.setStationCode(stationCode);
        station.setStationName(stationName);
        station.setStationStatus(Integer.parseInt(stationStatus));
        station.setApplication(application);
        station.setOnlineFlag(Integer.parseInt(onlineFlag));
        station.setStationIdDZ(stationIdDZ);
        station.setProtocol(Integer.parseInt(protocol));
        station.setProtocolName(protocolName);
        station.setPosition(position);
        station.setStreet(street);
        station.setDistrict(district);
        station.setRange(range);
        station.setCountryCon(Integer.parseInt(countryCon));
        station.setCityCon(Integer.parseInt(cityCon));
        station.setDomainCon(Integer.parseInt(domainCon));
        station.setArea(Integer.parseInt(area));
        station.setDomainCon(Integer.parseInt(domain));
        System.out.println(station.toString());
        Station result = stationDao.save(station);
        if (result != null){
            return "新增成功";
        }

        return "新增失败";
    }

    @Override
    public String deleteStationByStationId(String stationId) {
        Station station = stationDao.findByStationId(stationId);
        if (station == null){
            return "该站点信息不存在";
        }

        stationDao.deleteByStationId(stationId);
        return "删除成功";
    }

    @Override
    public String queryStationsByKey(String key) {

        JSONObject dataJSON = new JSONObject();
        JSONArray stationArray = new JSONArray();

        List<Station> stations = null;

        //没有输入模糊值则查询失败
        if (key == null || key.equals("")){
            return null;
        }

        stations = stationDao.findStationsByIdAndNameLike(key);

        if (stations.size() > 0){
            for (Station station : stations){
                JSONObject stationJSON = new JSONObject();
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("station_name", station.getStationName());
                stationArray.add(stationJSON);
            }

        }
        dataJSON.put("stations", stationArray);
        System.out.println(dataJSON.toJSONString());

        return dataJSON.toJSONString();


    }

    @Override
    public String querymDataByStationArea(QuerymDataByStationsAreaReq req) {

        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();


        if (req.getArea() > 5 || req.getArea() < 0){
            return null;
        }
        if (req.getPageSize() < 1){
            return null;
        }
        if (req.getPageNum() < 1){
            return null;
        }

        //符合区域环境的总数
        int count ;

        if (req.getArea() == 5 ){

            count = stationDao.queryAllStationNum();
        }
        else {
            count = stationDao.queryStationsNumByArea(req.getArea());
        }

        //分页查询站点
        int start = (req.getPageNum() - 1) * req.getPageSize();
        int end = req.getPageSize();

        List<Station> stations = null;

        if (req.getArea() == 5){
            stations = stationDao.queryStationsByPage(start, end);
        }
        else {
            stations = stationDao.queryStationsByAreaAndPage(req.getArea(), start, end);
        }

        for (Station station : stations){

            JSONObject stationJSON = new JSONObject();

            //查询数据表中是否有该站点的数据信息
            int existMdata = mDataDao.querymDataNumByStationId(station.getStationCode());

            if (existMdata == 0){
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("latest_time", "");
                stationJSON.put("count_r", 0);
                stationJSON.put("LA", "");
                stationJSON.put("LEQ", "");
                stationJSON.put("LMX","");
            }
            else {
                List<MData> mDatas = mDataDao.queryMaxTimeMdataByStationId(station.getStationCode());

                //查询当天站点收到的数据的数量
                Date date = new Date();
                int nowDayMdataNum = mDataDao.querymDataNumBetween(station.getStationCode(), DateUtil.getTodayStr(date), DateUtil.getDateStr(date));

                String LA = null;
                String LEQ = null;
                String LMX = null;

                for (MData mData : mDatas){
                    if (mData.getNorm_code().equals("n00000")){
                        LA = mData.getNorm_val();
                    }
                    if (mData.getNorm_code().equals("n00006")){
                        LEQ = mData.getNorm_val();
                    }
                    if (mData.getNorm_code().equals("n00010")){
                        LMX = mData.getNorm_val();
                    }
                }
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("latest_time", DateUtil.getDateStr(mDatas.get(0).getData_time()));
                stationJSON.put("count_r", nowDayMdataNum);
                stationJSON.put("LA", LA);
                stationJSON.put("LEQ", LEQ);
                stationJSON.put("LMX",LMX);
            }

            dataArray.add(stationJSON);
        }

        siteData.put("count", count);
        siteData.put("data", dataArray);

        dataJson.put("sitesDataReal", siteData);
        //打印语句上线要删除
        System.out.println(dataJson.toJSONString());
        return dataJson.toJSONString();
    }

    @Override
    public String queryhDataByStationArea(QueryhDataByStationAreaReq req) {

        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (req.getArea() > 5 || req.getArea() < 0){
            return null;
        }
        if (req.getPageSize() < 1){
            return null;
        }
        if (req.getPageNum() < 1){
            return null;
        }

        //符合区域环境的总数
        int count ;

        if (req.getArea() == 5 ){

            count = stationDao.queryAllStationNum();
        }
        else {
            count = stationDao.queryStationsNumByArea(req.getArea());
        }

        //分页查询站点
        int start = (req.getPageNum() - 1) * req.getPageSize();
        int end = req.getPageSize();
        List<Station> stations = null;

        if (req.getArea() == 5){
            stations = stationDao.queryStationsByPage(start, end);
        }
        else {
            stations = stationDao.queryStationsByAreaAndPage(req.getArea(), start, end);
        }

        for (Station station : stations){
            JSONObject stationJSON = new JSONObject();

            int existHdata = hDataDao.queryHdataNumByStationId(station.getStationCode());

            if (existHdata == 0 ){
                //没有该站点的数据记录
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("calibration_value","");
                stationJSON.put("flag", "");
                stationJSON.put("latest_time_h","");
                stationJSON.put("count_h", "");
                stationJSON.put("effective_rate_h", "");
                stationJSON.put("LEQ_h", "");
                stationJSON.put("LMX_h", "");
            }
            else {
                //查询当天的小时数据的条数
                Date date = new Date();
                int nowdayHdataNum = hDataDao.queryhDataNumBetween(station.getStationCode(), DateUtil.getTodayStr(date), DateUtil.getDateStr(date));

                List<HData> hDatas = hDataDao.queryMaxTimeHdataByStationId(station.getStationCode());

                String calibration_value = null;
                String flag = null;
                String effective_rate_h  = null;
                String LEQ_h = null;
                String LMX_h = null;

                for (HData hdata : hDatas){
                    if (hdata.getNorm_code().equals("n00100")){
                        calibration_value = hdata.getNorm_val();
                        flag = hdata.getNorm_flag();
                    }
                    if (hdata.getNorm_code().equals("n00006")){
                        LEQ_h = hdata.getNorm_val();
                    }
                    if (hdata.getNorm_code().equals("n00010")){
                        LMX_h = hdata.getNorm_val();
                    }
                }

                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("calibration_value",calibration_value);
                stationJSON.put("flag", flag);
                stationJSON.put("latest_time_h",DateUtil.getDateStr(hDatas.get(0).getData_time()));
                stationJSON.put("count_h", nowdayHdataNum);
                stationJSON.put("effective_rate_h", effective_rate_h);
                stationJSON.put("LEQ_h", LEQ_h);
                stationJSON.put("LMX_h", LMX_h);
            }

            dataArray.add(stationJSON);
        }

        siteData.put("count", count);
        siteData.put("data", dataArray);

        dataJson.put("sitesDataHour", siteData);

        System.out.println(dataJson.toJSONString());
        return dataJson.toJSONString();
    }

    @Override
    public String querydDataByStationArea(QuerydDataByStationAreaReq req) {

        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (req.getArea() > 5 || req.getArea() < 0){
            return null;
        }
        if (req.getPageSize() < 1){
            return null;
        }
        if (req.getPageNum() < 1){
            return null;
        }

        //符合区域环境的总数
        int count ;

        if (req.getArea() == 5 ){

            count = stationDao.queryAllStationNum();
        }
        else {
            count = stationDao.queryStationsNumByArea(req.getArea());
        }

        //分页查询站点
        int start = (req.getPageNum() - 1) * req.getPageSize();
        int end = req.getPageSize();

        List<Station> stations = null;

        if (req.getArea() == 5){
            stations = stationDao.queryStationsByPage(start, end);
        }
        else {
            stations = stationDao.queryStationsByAreaAndPage(req.getArea(), start, end);
        }

        for(Station station : stations){

            JSONObject stationJSON = new JSONObject();

            int existDdata = dDataDao.queryDdataNumByStationId(station.getStationCode());

            if (existDdata == 0 ){
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("Ld", "");
                stationJSON.put("effective_rate_Ld", "");
                stationJSON.put("Ln", "");
                stationJSON.put("effective_rate_Ln", "");
                stationJSON.put("Lnm", "");
            }
            else {

                //查询最新的日数据
                List<DData> dDatas = dDataDao.queryMaxTimeDdataByStationId(station.getStationCode());

                String Ld = null;
                String effective_rate_Ld = null;
                String Ln = null;
                String effective_rate_Ln = null;
                String Lnm = null;

                for (DData dData : dDatas){
                    if (dData.getNorm_code().equals("n00008")){
                        Ld = dData.getNorm_val();
                    }
                    if (dData.getNorm_code().equals("n00009")){
                        Ln = dData.getNorm_val();
                    }
                    if (dData.getNorm_code().equals("n00021")){
                        Lnm = dData.getNorm_val();
                    }
                }

                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("Ld", Ld);
                stationJSON.put("effective_rate_Ld", "");
                stationJSON.put("Ln", Ln);
                stationJSON.put("effective_rate_Ln", "");
                stationJSON.put("Lnm", Lnm);
            }

            dataArray.add(stationJSON);
        }

        siteData.put("count", count);
        siteData.put("data", dataArray);

        dataJson.put("sitesDataDay", siteData);
        System.out.println(dataJson.toJSONString());

        return dataJson.toJSONString();
    }

    @Override
    public Station queryStatiionByCode(String stationCode) {
        return stationDao.findByStationCode(stationCode);
    }
}
