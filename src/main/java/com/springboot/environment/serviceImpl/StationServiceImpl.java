package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.JSONPResponseBodyAdvice;
import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao stationDao;


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
    public List<Station> queryStationsByCodeLike(String stationCode) {
        return stationDao.finByStationCodeLike(stationCode);
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
        Station station = stationDao.findStationByStationId(stationId);
        if (station == null){
            return "该站点信息不存在";
        }

        stationDao.deleteByStationId(stationId);
        return "删除成功";
    }

    @Override
    public Map getDomainFromStation() {
        Map<String, List> map = new LinkedHashMap<String, List>();
        List<String> funcCodes=stationDao.getFuncCodes();
        map.put("funcCodes",funcCodes);
        return map;
    }

    @Override
    public Map getStationsByAreasAndFuncCodes(Map<String, Object> params) {
        Map<String, List> map = new LinkedHashMap<String, List>();


        Map<String,Object> query=(Map<String, Object>) params.get("query");
        System.out.println(query);


        ArrayList areas=(ArrayList) query.get("areas");
        ArrayList funcCodes=(ArrayList) query.get("funcCodes");
        System.out.println(areas+" "+funcCodes);
        String areas_checkedAll=query.get("areas_checkedAll")+"";
        String funcCodes_checkedAll=query.get("funcCodes_checkedAll")+"";
        System.out.println(areas_checkedAll+" "+funcCodes_checkedAll);
        List<Map> innerMapList=new ArrayList<Map>();
        if(areas_checkedAll.equals("false")){
            //循环列表中的areas--不是全部的areas，需要遍历，然后判断funcCodes--不是全部的func，需要遍历
            if(funcCodes_checkedAll.equals("false")){
                for(int i=0;i<areas.size();i++){
                    List<Station> stations=stationDao.getAreasByAreasName(areas.get(i));
                    for (Station station:stations) {
                        for(int j=0;j<funcCodes.size();j++){//从获得的stationslist中查找功能让区为get(j)的站点，并把这个站点加入到list中
                            if(funcCodes.get(j).equals((station.getDomain()+""))){
                                Map<String, String> innerMap = new LinkedHashMap<String, String>();
                                innerMap.put("station_id",station.getStationCode());
                                innerMap.put("station_name",station.getStationName());
                                innerMapList.add(innerMap);
                            }
                        }
                    }
                }
            }else{//列表中不是全部的areas，但是是全部的func
                for(int i=0;i<areas.size();i++){
                    List<Station> stations=stationDao.getAreasByAreasName(areas.get(i));
                    for (Station station:stations) {
                        Map<String, String> innerMap = new LinkedHashMap<String, String>();
                        innerMap.put("station_id",station.getStationId());
                        innerMap.put("station_name",station.getStationName());
                        innerMapList.add(innerMap);
                    }
                }
            }
        }else {
            //获取所有的areas，然后再判断funcCodes
            List<Station> stations=stationDao.findAll();
            if(funcCodes_checkedAll.equals("false")){
                for (Station station:stations) {
                    for(int j=0;j<funcCodes.size();j++){//从获得的stationslist中查找功能让区为get(j)的站点，并把这个站点加入到list中
                        if(funcCodes.get(j).equals((station.getDomain()+""))){
                            Map<String, String> innerMap = new LinkedHashMap<String, String>();
                            innerMap.put("station_id",station.getStationCode());
                            innerMap.put("station_name",station.getStationName());
                            innerMapList.add(innerMap);
                        }
                    }
                }
            }else{
                for (Station station:stations) {
                    Map<String, String> innerMap = new LinkedHashMap<String, String>();
                    innerMap.put("station_id",station.getStationCode());
                    innerMap.put("station_name",station.getStationName());
                    innerMapList.add(innerMap);
                }
            }
        }
        System.out.println(innerMapList.size());
        map.put("stations",innerMapList);
        return map;
    }

    @Override
    public String queryStationsByKey(String key) {

        JSONObject all = new JSONObject();
        List<Station> allStations = null;

        if (key == "" || key == null){
            allStations = stationDao.findAll();
        }
        else {
            allStations = stationDao.queryStationsByKey(key);
        }

        JSONArray stationArray = new JSONArray();
        for (Station station : allStations){
            JSONObject stationObject = new JSONObject();
            stationObject.put("station_id", station.getStationId());
            stationObject.put("station_name", station.getStationName());

            stationArray.add(stationObject);
        }

        all.put("stations", stationArray);

        return all.toJSONString();
    }

    @Override
    public String querymDataByStationArea(QuerymDataByStationsAreaReq req) {

        if (req.getArea() < 0 || req.getArea() > 4){
            return null;
        }
        //查询符合条件的站点数量
        int stationNum = stationDao.queryStationNumByArea(req.getArea());

        //分页查询符合条件的站点信息
        if (req.getPageNum() < 1){
            return null;
        }
        int startNum = (req.getPageNum() - 1) * req.getPageSize();

        List<Station> stationList = stationDao.queryStationsByAreaAndPage(req.getArea(), startNum, req.getPageSize());

        for(Station station : stationList){
            //查询总数
            Date date = new Date();
            int mDataSumToday = stationDao.querymDataNumBetween(station.getStationId(), DateUtil.getDateStr(date), DateUtil.getTodayStr(date));



        }

        return null;
    }
}
