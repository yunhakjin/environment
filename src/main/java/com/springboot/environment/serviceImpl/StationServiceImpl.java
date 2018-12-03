package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.*;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StationConstant;
import com.springboot.environment.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Autowired
    StationDao stationDao;

    @Autowired
    MDataDao mDataDao;

    @Autowired
    DDataDao dDataDao;

    @Autowired
    NormDao normDao;

    @Autowired
    HDataDao hDataDao;

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

        JSONObject dataJSON = new JSONObject();
        JSONArray stationArray = new JSONArray();

        List<Station> stations = null;

        //没有输入模糊值则查询失败
        if (key == null || key.equals("")){
            return null;
        }

        stations = stationDao.findStationsByIdAndNameLike(key);

        if (!StringUtil.isNullOrEmpty(stations)){
            for (Station station : stations){
                JSONObject stationJSON = new JSONObject();
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_code", station.getStationCode());
                stationArray.add(stationJSON);
            }

        }
        dataJSON.put("stations", stationArray);
        System.out.println(dataJSON.toJSONString());
        System.out.println(stations.size());

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

            List<MData> mDatas = mDataDao.queryMaxTimeMdataByStationId(station.getStationCode());

            if (StringUtil.isNullOrEmpty(mDatas)){
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("station_code", station.getStationCode());
                stationJSON.put("latest_time", "");
                stationJSON.put("count_r", 0);
                stationJSON.put("LA", "");
                stationJSON.put("LEQ", "");
                stationJSON.put("LMX","");
            }
            else {
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
                stationJSON.put("station_code", station.getStationCode());
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

            List<HData> hDatas = hDataDao.queryMaxTimeHdataByStationId(station.getStationCode());
            if (StringUtil.isNullOrEmpty(hDatas)){
                //没有该站点的数据记录
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("station_code", station.getStationCode());
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
                stationJSON.put("station_code", station.getStationCode());
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

            List<DData> dDatas = dDataDao.queryMaxTimeDdataByStationId(station.getStationCode());
            if (StringUtil.isNullOrEmpty(dDatas)){
                stationJSON.put("station_name", station.getStationName());
                stationJSON.put("station_id", station.getStationId());
                stationJSON.put("station_code", station.getStationCode());
                stationJSON.put("Ld", "");
                stationJSON.put("effective_rate_Ld", "");
                stationJSON.put("Ln", "");
                stationJSON.put("effective_rate_Ln", "");
                stationJSON.put("Lnm", "");
            }
            else {

                //查询最新的日数据
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
                stationJSON.put("station_code", station.getStationCode());
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

    @Override
    public List<String> getAllStreet() {
        return stationDao.getAllStreet();
    }

    public void insertStation(Station station,String setupdate) {
        String application=station.getApplication();
        int area=station.getArea();
        int city_con=station.getCityCon();
        int country_con=station.getCountryCon();
        String district=station.getDistrict();
        int domain=station.getDomain();
        int domain_con=station.getDomainCon();
        String station_code=station.getStationCode();
        String station_id=station.getStationId();
        String station_id_dz=station.getStationIdDZ();
        String station_name=station.getStationName();
        int station_status=station.getStationStatus();
        int online_flag=station.getOnlineFlag();
        int protocol=station.getProtocol();
        String protocol_name=station.getStationName();
        String street=station.getStreet();
        String station_major=station.getStation_major();
        String station_setup=station.getStation_setup();
        String station_setupdate=setupdate;
        String company_code=station.getCompany_code();
        int climate=station.getClimate();
        int radar=station.getRadar();
        String station_position=station.getPosition();
        String station_range=station.getRange();
        int station_attribute=station.getStation_attribute();
        stationDao.insertStation(application,area,city_con,country_con,district,domain,domain_con,station_code,
                station_id,station_id_dz,station_name,station_status,online_flag,protocol,protocol_name,street,station_major,
                station_setup,station_setupdate,company_code,climate,radar,station_position,station_range,station_attribute);
    }

    @Override
    public void deleteStation(String station_id) {
        stationDao.deleteStation(station_id);
    }

    @Override
    public void updateStation(Station station, String setupdate, String target) {
        String application=station.getApplication();
        int area=station.getArea();
        int city_con=station.getCityCon();
        int country_con=station.getCountryCon();
        String district=station.getDistrict();
        int domain=station.getDomain();
        int domain_con=station.getDomainCon();
        String station_code=station.getStationCode();
        String station_id=station.getStationId();
        String station_id_dz=station.getStationIdDZ();
        String station_name=station.getStationName();
        int station_status=station.getStationStatus();
        int online_flag=station.getOnlineFlag();
        int protocol=station.getProtocol();
        String protocol_name=station.getStationName();
        String street=station.getStreet();
        String station_major=station.getStation_major();
        String station_setup=station.getStation_setup();
        String station_setupdate=setupdate;
        String company_code=station.getCompany_code();
        int climate=station.getClimate();
        int radar=station.getRadar();
        String station_position=station.getPosition();
        String station_range=station.getRange();
        int station_attribute=station.getStation_attribute();
        stationDao.updateStation(area,application,city_con,country_con,district,domain,domain_con,station_code,
                station_id,station_id_dz,station_name,station_status,online_flag,protocol,protocol_name,street,station_major,
                station_setup,station_setupdate,company_code,climate,radar,station_position,station_range,station_attribute,target);
    }

    @Override
    public Station getByStationId(String station_id) {
        return stationDao.findByStationId(station_id);
    }

    @Override
    public Map GEOJson(Map params) {
        String type=params.get("type")+"";
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map> lists=new ArrayList<Map>();
        if(type.equals("all")){
            List<Station> stations=stationDao.findAll();
            String LeqAnorm_code=normDao.getLeqACode().toString();
            //String VdrNorm_code=normDao.getVdrCode();
            for(int i=0;i<stations.size();i++){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("type","Feature");
                map.put("id",stations.get(i).getStationCode());
                map.put("name",stations.get(i).getStationName());
                map.put("region",stations.get(i).getDistrict());
                List<HData> hDatas= hDataDao.getLatestStationListByStationCode(stations.get(i).getStationCode());
                System.out.println(hDatas);
                map.put("time",(hDataDao.getLatestTimeByStationCode(stations.get(i).getStationCode().toString())));
                for (int j= 0;j<hDatas.size();j++){
                    if(hDatas.get(j).getNorm_code().equals(LeqAnorm_code)){
                        map.put("LeqA",hDatas.get(j).getNorm_val());
                    }
                }
                //M_type和C_type暂时未处理
                map.put("M_type","");
                map.put("C_type","");
                if(stations.get(i).getOnlineFlag()==1){
                    map.put("S_type","在线");
                }else{
                    map.put("S_type","离线");
                }

                if(stations.get(i).getStation_attribute()==1){
                    map.put("O_status","自动");
                }else if(stations.get(i).getStation_attribute()==0){
                    map.put("O_status","手动");
                }
                Map<String,Object> mapGeometry=new HashMap<String,Object>();
                mapGeometry.put("type","Point");
                List<String> coordinates=new ArrayList<>();
                String[] coordinates_str=stations.get(i).getPosition().split(",");
                for(int k=0;k<coordinates_str.length;k++){
                    coordinates.add(coordinates_str[k]);
                }
                mapGeometry.put("coordinates",coordinates);
                map.put("geometry",mapGeometry);
                lists.add(map);
            }
        }
        resultMap.put("type","FeatureCollection");
        resultMap.put("features",lists);
        return resultMap;
    }
}
