package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.*;
import com.springboot.environment.request.ComprehensiveQueryRequest;
import com.springboot.environment.request.QuerydDataByStationAreaReq;
import com.springboot.environment.request.QueryhDataByStationAreaReq;
import com.springboot.environment.request.QuerymDataByStationsAreaReq;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StationConstant;
import com.springboot.environment.util.StringUtil;
import org.hibernate.event.service.spi.EventListenerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.text.SimpleDateFormat;

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

    @Autowired
    GatherDao gatherDao;

    @Autowired
    GatherDataDao gatherDataDao;

    @Autowired
    LogOffLineDao logOffLineDao;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MDataBasicDao mDataBasicDao;

    private static final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);
    @Autowired
    WarningServiceImp warningServiceImp;

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
    public Map getStationsByAreasAndFuncCodes(Map<String, Object> params, String operation_id) {
        Map<String, List> map = new LinkedHashMap<String, List>();
        Map<String,Object> query=(Map<String, Object>) params.get("query");
        System.out.println(query);
        ArrayList areas=(ArrayList) query.get("areas");
        ArrayList funcCodes=(ArrayList) query.get("funcCodes");
        System.out.println(areas+" "+funcCodes);
        String areas_checkedAll=query.get("areas_checkedAll")+"";
        String funcCodes_checkedAll=query.get("funcCodes_checkedAll")+"";
        System.out.println(areas_checkedAll+" "+funcCodes_checkedAll);
        //List<Station> stationss=userServiceImpl.GetStationListByUser();
        List<Map> innerMapList=new ArrayList<Map>();
        if(areas_checkedAll.equals("false")){
            //循环列表中的areas--不是全部的areas，需要遍历，然后判断funcCodes--不是全部的func，需要遍历
            if(funcCodes_checkedAll.equals("false")){
                for(int i=0;i<areas.size();i++){
                    List<Station> stations=stationDao.getAreasByAreasName(areas.get(i));
                    for (Station station:stations) {
                        if(operation_id.equals("0")){
                            for(int j=0;j<funcCodes.size();j++){//从获得的stationslist中查找功能让区为get(j)的站点，并把这个站点加入到list中
                                if(funcCodes.get(j).equals((station.getDomain()+""))){
                                    Map<String, String> innerMap = new LinkedHashMap<String, String>();
                                    innerMap.put("station_id",station.getStationCode());
                                    innerMap.put("station_name",station.getStationName());
                                    innerMapList.add(innerMap);
                                }
                            }
                        }else{
                            if(station.getOperation_id().equals(operation_id)){
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
                    }
                }
            }else{//列表中不是全部的areas，但是是全部的func
                for(int i=0;i<areas.size();i++){
                    List<Station> stations=stationDao.getAreasByAreasName(areas.get(i));

                    for (Station station:stations) {
                        if(operation_id.equals("0")){
                            Map<String, String> innerMap = new LinkedHashMap<String, String>();
                            innerMap.put("station_id",station.getStationCode());
                            innerMap.put("station_name",station.getStationName());
                            innerMapList.add(innerMap);
                        }else{
                            if(station.getOperation_id().equals(operation_id)){
                                Map<String, String> innerMap = new LinkedHashMap<String, String>();
                                innerMap.put("station_id",station.getStationCode());
                                innerMap.put("station_name",station.getStationName());
                                innerMapList.add(innerMap);
                            }
                        }

                    }
                }
            }
        }else {
            //获取所有的areas，然后再判断funcCodes
            List<Station> stations=stationDao.findAll();
            if(funcCodes_checkedAll.equals("false")){
                for (Station station:stations) {
                    if(operation_id.equals("0")){
                        for(int j=0;j<funcCodes.size();j++){//从获得的stationslist中查找功能让区为get(j)的站点，并把这个站点加入到list中
                            if(funcCodes.get(j).equals((station.getDomain()+""))){
                                Map<String, String> innerMap = new LinkedHashMap<String, String>();
                                innerMap.put("station_id",station.getStationCode());
                                innerMap.put("station_name",station.getStationName());
                                innerMapList.add(innerMap);
                            }
                        }
                    }else{
                        /*System.out.println("stationCode666:::"+station.getStationCode());
                        System.out.println(station.getOperation_id().equals(operation_id));
                        System.out.println(station.getOperation_id());
                        System.out.println(operation_id);*/
                        if(station.getOperation_id().equals(operation_id)){
                            for(int j=0;j<funcCodes.size();j++){//从获得的stationslist中查找功能让区为get(j)的站点，并把这个站点加入到list中
                                if(funcCodes.get(j).equals((station.getDomain()+""))){
                                    Map<String, String> innerMap = new LinkedHashMap<String, String>();
                                    innerMap.put("station_id",station.getStationCode());
                                    innerMap.put("station_name",station.getStationName());
                                    innerMapList.add(innerMap);
                                }
                            }
                        }
                        //System.out.println(innerMapList);
                    }

                }
            }else{
                for (Station station:stations) {
                    if(operation_id.equals("0")){
                        Map<String, String> innerMap = new LinkedHashMap<String, String>();
                        innerMap.put("station_id",station.getStationCode());
                        innerMap.put("station_name",station.getStationName());
                        innerMapList.add(innerMap);
                    }else{
                        if(station.getOperation_id().equals(operation_id)){
                            Map<String, String> innerMap = new LinkedHashMap<String, String>();
                            innerMap.put("station_id",station.getStationCode());
                            innerMap.put("station_name",station.getStationName());
                            innerMapList.add(innerMap);
                        }
                    }

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
    public String querymDataByStationArea(Map<String, Object> params, HttpSession session) {

        //方法开始时间
        long startTime  = System.currentTimeMillis();
        Date nowDate = new Date();

        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        ComprehensiveQueryRequest request = buildReq(params, session);
        List<Station> stations = comprehensiveQueryStations(request);
        int count = getComprehensiveQueryNum(request);

        if (!StringUtil.isNullOrEmpty(stations)) {
            for (Station station : stations) {

                JSONObject stationJSON = new JSONObject();

                //取得当天的数据条数
                int thisDayCount = mDataDao.querymDataNumBetween(station.getStationCode(), DateUtil.getTodayStr(nowDate), DateUtil.getDateStr(nowDate));
                //取得第一个数据
                Set<String> maxScoreMdata = zSetOperations.reverseRange(station.getStationCode(), 0, 0);
                String maxDataTime = null;

                //如果redis中存在最新的数据
                if (!maxScoreMdata.isEmpty()) {
                    //最新数据的json格式
                    JSONObject maxMdataJson = JSONObject.parseObject(maxScoreMdata.iterator().next());

                    //最新数据的时间，格式为yyyy-MM-dd HH:mm:ss.000
                    //最新数据的时间戳
                    maxDataTime = (String) maxMdataJson.get("data_time");
                    maxDataTime = maxDataTime.split("\\.")[0];
                    maxDataTime = DateUtil.getTimeUntilMM(maxDataTime);

                    String LA = null;
                    String LEQ = null;
                    String LMX = null;

                    JSONArray data = maxMdataJson.getJSONArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        JSONObject indexData = data.getJSONObject(i);
                        if (indexData.get("code").equals("n00000")) {
                            LA = (String) indexData.get("Val");
                        }
                        if (indexData.get("code").equals("n00006")) {
                            LEQ = (String) indexData.get("Val");
                        }
                        if (indexData.get("code").equals("n00010")) {
                            LMX = (String) indexData.get("Val");
                        }
                    }
                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("latest_time", maxDataTime);
                    stationJSON.put("count_r", thisDayCount == 0 ? "" : thisDayCount);
                    stationJSON.put("LA", LA);
                    stationJSON.put("LEQ", LEQ);
                    stationJSON.put("LMX", LMX);
                } else {
                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("latest_time", "");
                    stationJSON.put("count_r", thisDayCount == 0 ? "" : thisDayCount);
                    stationJSON.put("LA", "");
                    stationJSON.put("LEQ", "");
                    stationJSON.put("LMX", "");
                }

                dataArray.add(stationJSON);
            }
        }

        siteData.put("count", count);
        siteData.put("data", dataArray);

        dataJson.put("sitesDataReal", siteData);
        logger.info("返回数据 {}", dataJson.toJSONString());
        logger.info("方法耗时 {}" , (System.currentTimeMillis() - startTime) + "毫秒");
        return dataJson.toJSONString();
    }

    @Override
    public String queryhDataByStationArea(Map<String, Object> params, HttpSession session) {

        long startTime = System.currentTimeMillis();

        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();

        ComprehensiveQueryRequest request = buildReq(params, session);
        List<Station> stations = comprehensiveQueryStations(request);
        int count = getComprehensiveQueryNum(request);

        if (!StringUtil.isNullOrEmpty(stations)) {
            for (Station station : stations) {
                JSONObject stationJSON = new JSONObject();

                List<HData> hDatas = hDataDao.queryMaxTimeHdataByStationId(station.getStationCode());
                if (StringUtil.isNullOrEmpty(hDatas)) {
                    //没有该站点的数据记录
                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("calibration_value", "");
                    stationJSON.put("flag", "");
                    stationJSON.put("latest_time_h", "");
                    stationJSON.put("count_h", "");
                    stationJSON.put("effective_rate_h", "");
                    stationJSON.put("LEQ_h", "");
                    stationJSON.put("LMX_h", "");
                } else {
                    //查询当天的小时数据的条数
                    Date date = new Date();
                    int nowdayHdataNum = hDataDao.queryhDataNumBetween(station.getStationCode(), DateUtil.getTodayStr(date), DateUtil.getDateStr(date));

                    String calibration_value = null;
                    String flag = null;
                    String effective_rate_h = null;
                    String LEQ_h = null;
                    String LMX_h = null;

                    for (HData hdata : hDatas) {
                        if (hdata.getNorm_code().equals("n00100")) {
                            calibration_value = hdata.getNorm_val();
                            flag = hdata.getNorm_flag();
                        }
                        if (hdata.getNorm_code().equals("n00006")) {
                            LEQ_h = hdata.getNorm_val();
                        }
                        if (hdata.getNorm_code().equals("n00010")) {
                            LMX_h = hdata.getNorm_val();
                            effective_rate_h = hdata.getNorm_vdr();
                        }
                    }

                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("calibration_value", calibration_value);
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("flag", flag);
                    stationJSON.put("latest_time_h", DateUtil.getDateBeforeSecond(hDatas.get(0).getData_time()));
                    stationJSON.put("count_h", nowdayHdataNum);
                    stationJSON.put("effective_rate_h", StringUtil.convertStringToInt(effective_rate_h));
                    stationJSON.put("LEQ_h", LEQ_h);
                    stationJSON.put("LMX_h", LMX_h);
                }
                dataArray.add(stationJSON);
            }
        }

        siteData.put("count", count);
        siteData.put("data", dataArray);

        dataJson.put("sitesDataHour", siteData);

        logger.info("返回数据 {}", dataJson.toJSONString());
        logger.info("方法耗时 {}" , (System.currentTimeMillis() - startTime) + "毫秒");
        return dataJson.toJSONString();
    }

    @Override
    public String querydDataByStationArea(Map<String, Object> params, HttpSession session) {

        long startTime = System.currentTimeMillis();
        JSONObject dataJson = new JSONObject();
        JSONObject siteData = new JSONObject();
        JSONArray dataArray = new JSONArray();

        ComprehensiveQueryRequest request = buildReq(params, session);
        List<Station> stations = comprehensiveQueryStations(request);
        int count = getComprehensiveQueryNum(request);

        if (!StringUtil.isNullOrEmpty(stations)) {
            for (Station station : stations) {

                JSONObject stationJSON = new JSONObject();

                List<DData> dDatas = dDataDao.queryMaxTimeDdataByStationId(station.getStationCode());
                if (StringUtil.isNullOrEmpty(dDatas)) {
                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("Ld", "");
                    stationJSON.put("effective_rate_Ld", "");
                    stationJSON.put("latest_time_d", "");
                    stationJSON.put("Ln", "");
                    stationJSON.put("effective_rate_Ln", "");
                    stationJSON.put("Lnm", "");
                } else {

                    //查询最新的日数据
                    String Ld = null;
                    String effective_rate_Ld = null;
                    String Ln = null;
                    String effective_rate_Ln = null;
                    String Lnm = null;

                    for (DData dData : dDatas) {
                        if (dData.getNorm_code().equals("n00008")) {
                            Ld = dData.getNorm_val();
                            effective_rate_Ld = dData.getNorm_vdr();
                        }
                        if (dData.getNorm_code().equals("n00009")) {
                            Ln = dData.getNorm_val();
                            effective_rate_Ln = dData.getNorm_vdr();
                        }
                        if (dData.getNorm_code().equals("n00021")) {
                            Lnm = dData.getNorm_val();
                        }
                    }

                    stationJSON.put("station_name", station.getStationName());
                    stationJSON.put("station_id", station.getStationId());
                    stationJSON.put("station_code", station.getStationCode());
                    stationJSON.put("sim", station.getStationSim());
                    stationJSON.put("Ld", Ld);
                    stationJSON.put("latest_time_d", DateUtil.getDateBeforeHour(dDatas.get(0).getData_time()));
                    stationJSON.put("effective_rate_Ld", StringUtil.convertStringToInt(effective_rate_Ld));
                    stationJSON.put("Ln", Ln);
                    stationJSON.put("effective_rate_Ln", StringUtil.convertStringToInt(effective_rate_Ln));
                    stationJSON.put("Lnm", Lnm);
                }

                dataArray.add(stationJSON);
            }
        }
        siteData.put("count", count);
        siteData.put("data", dataArray);
        dataJson.put("sitesDataDay", siteData);
        logger.info("返回数据 {}", dataJson.toJSONString());
        logger.info("方法耗时 {}" , (System.currentTimeMillis() - startTime) + "毫秒");
        return dataJson.toJSONString();
    }

    @Override
    public List<Station> comprehensiveQueryStations(ComprehensiveQueryRequest request) {
        //分页查询站点
        int start = (request.getPageNum() - 1) * request.getPageSize();
        int end = request.getPageSize();
        //综合查询站点信息，不包含在线离线判断
        List<Station> stations = stationDao.comprehensiveQueryByPage(request.getArea(), request.getEnvironment(), request.getIsCountry(),
                request.getIsCity(), request.getIsArea(), request.getAttribute(), request.getDistrict(), request.getStreet(), request.getUserOperationId(), start, end);
        //在线离线判断，如果为null则表示查询全部的站点信息
        //查询在线标识,需要循环判断
        List<Station> newList = new ArrayList<>();
        if (request.getState() == null) {
            newList = stations;
        }
        else if (request.getState().equals("1")) {
            if (!StringUtil.isNullOrEmpty(stations)) {
                for (Station station : stations) {
                    LogOffLine logOffLine = logOffLineDao.findByStationOrGatherID(station.getStationCode());
                    if (logOffLine == null || logOffLine.getFlag() == 1) {
                        newList.add(station);
                    }
                }
            }
        } else if (request.getState().equals("0")) {
            if (!StringUtil.isNullOrEmpty(stations)) {
                for (Station station : stations) {
                    LogOffLine logOffLine = logOffLineDao.findByStationOrGatherID(station.getStationCode());
                    if (logOffLine != null && logOffLine.getFlag() == 0) {
                        newList.add(station);
                    }
                }
            }
        }
        //符合区域环境的总数
        int count = newList.size();
        logger.info("符合条件的站点信息={}, 总数={}", newList.toString(), count);
        return newList;
    }

    private ComprehensiveQueryRequest buildReq(Map<String, Object> params, HttpSession session) {
        ComprehensiveQueryRequest request = new ComprehensiveQueryRequest();
        //area是区域环境
        String area = (String)params.get("area");
        String environment = (String)params.get("environment");
        String control = (String) params.get("c");
        String state = (String) params.get("s");
        String attribute = (String)params.get("o");
        List position = (List)params.get("street");
        String district = (String)position.get(0);
        String street = (String)position.get(1);
        int pageSize = (Integer) params.get("each_page_num");
        int pageNum = (Integer) params.get("current_page");
        User user = (User) session.getAttribute("user");

        if (area.equals("5")) {
            request.setArea(null);
        }
        else {
            request.setArea(area);
        }

        if (environment.equals("5")) {
            request.setEnvironment(null);
        }else {
            request.setEnvironment(environment);
        }
        //如果是全部则不做设置,默认的是初始化空值
        if (control.equals("国控")) {
            request.setIsCountry("1");
        }
        else if (control.equals("市控")) {
            request.setIsCity("1");
        }
        else if (control.equals("区控")) {
            request.setIsArea("1");
        }

        if (state.equals("在线")){
            request.setState("1");
        } else if (state.equals("离线")){
            request.setState("0");
        }

        if (attribute.equals("自动")){
            request.setAttribute("1");
        }else if (attribute.equals("手动")){
            request.setAttribute("0");
        }
        request.setDistrict(district);
        request.setStreet(street);
        request.setPageSize(pageSize);
        request.setPageNum(pageNum);
        if (user != null) {
            request.setUserOperationId(user.getOperation_id());
        }
        logger.info("构造的参数为{}", request.toString());

        return request;
    }

    private int getComprehensiveQueryNum(ComprehensiveQueryRequest request) {

        return stationDao.queryStationMunByComprehensiveQuery(request.getArea(), request.getEnvironment(), request.getIsCountry(),
                request.getIsCity(), request.getIsArea(), request.getAttribute(), request.getDistrict(), request.getStreet(), request.getUserOperationId());
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
        String station_sim=station.getStationSim();
        String operation_id=station.getOperation_id();
        stationDao.insertStation(application,area,city_con,country_con,district,domain,domain_con,station_code,
                station_id,station_id_dz,station_name,station_status,online_flag,protocol,protocol_name,street,station_major,
                station_setup,station_setupdate,company_code,climate,radar,station_position,station_range,station_attribute,station_sim,operation_id);
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
        String station_sim=station.getStationSim();
        String operation_id=station.getOperation_id();
        stationDao.updateStation(area,application,city_con,country_con,district,domain,domain_con,station_code,
                station_id,station_id_dz,station_name,station_status,online_flag,protocol,protocol_name,street,station_major,
                station_setup,station_setupdate,company_code,climate,radar,station_position,station_range,station_attribute,station_sim,operation_id,target);
    }

    @Override
    public Station getByStationId(String station_id) {
        return stationDao.findByStationId(station_id);
    }

    @Override
    public Map GEOJson(Map params, String operation_id) {
        String type=params.get("type")+"";
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map> lists=new ArrayList<Map>();
        Map<Object, Object> police = warningServiceImp.getRealWarning();
        Map<Object, Object> realWarningData=(Map<Object, Object>) police.get("realWarningData");
        List<Map> policeDatas = (List<Map>) realWarningData.get("data");



        if(type.equals("all")){
            //station
            List<Station> stations=stationDao.findAll();
            String LeqAnorm_code=normDao.getLeqACode().toString();
            for(int i=0;i<stations.size();i++){
                if(operation_id.equals("0")){
                    //用户为admin或者无运营单位  为0，能看所有站点
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("type","Feature");
                    map.put("id",stations.get(i).getStationCode());
                    map.put("name",stations.get(i).getStationName());
                    map.put("region",stations.get(i).getDistrict());
                    //map.put("OverLimit","否");
                    List<HData> hDatas= hDataDao.getLatestStationListByStationCode(stations.get(i).getStationCode());
                    if(hDatas!=null){
                        String time=(hDataDao.getLatestTimeByStationCode(stations.get(i).getStationCode().toString()));
                        boolean flag= false;
                        for(int tt=0;tt<hDatas.size();tt++){
                            if(hDatas.get(tt).getNorm_code().equals(LeqAnorm_code)){
                                flag=true;
                                break;
                            }
                        }
                        if(flag==true){
                            if(time!=null){
                                map.put("time",(time.substring(0,time.length()-2)));
                                for (int j= 0;j<hDatas.size();j++){
                                    if(hDatas.get(j).getNorm_code().equals(LeqAnorm_code)){
                                        map.put("LeqA",hDatas.get(j).getNorm_val());
                                        break;
                                    }else{
                                        map.put("LeqA","0");
                                    }
                                }
                            }
                        }else{
                            System.out.println("不存在最新数据");
                            map.put("time",sdf.format(new Date()));
                            map.put("LeqA","0");
                        }
                    }
                    List<String> MType_list=new ArrayList<String>();
                    MType_list.add("区域环境");
                    MType_list.add("功能区");
                    map.put("M_type",MType_list);

                    List<String> MType_value_list=new ArrayList<String>();
                    MType_value_list.add(""+stations.get(i).getArea());
                    MType_value_list.add(""+stations.get(i).getDomain());
                    map.put("M_typeValue",MType_value_list);



                    List<String> CType_list=new ArrayList<String>();
                    if(stations.get(i).getCountryCon()==1){
                        CType_list.add("国控");
                    }
                    if(stations.get(i).getCityCon()==1){
                        CType_list.add("市控");
                    }
                    if(stations.get(i).getDomainCon()==1){
                        CType_list.add("区控");
                    }
                    map.put("C_type",CType_list);
                    //待会修改

                    LogOffLine logOffLine=logOffLineDao.findByStationOrGatherID(stations.get(i).getStationCode());
                    if(logOffLine!=null){
                        if(logOffLine.getFlag()==1){
                            map.put("S_type","在线");
                        }else if(logOffLine.getFlag()==0){
                            map.put("S_type","离线");
                        }
                    }else{
                        map.put("S_type","在线");
                    }
                    if(stations.get(i).getStation_attribute()==1){
                        map.put("O_status","自动");
                    }else if(stations.get(i).getStation_attribute()==0){
                        map.put("O_status","手动");
                    }

                    if(policeDatas.isEmpty()){
                        map.put("OverLimit","否");
                    }else{
                        //报警  是否超标
                        for (int p=0;p< policeDatas.size();p++) {
                            Map<Object,Object> policeData= policeDatas.get(p);
                            System.out.println(policeDatas.get(p));
                            System.out.println((policeData.get("station_id").equals(stations.get(i).getStationCode())));
                            System.out.println("police:"+policeData.get("station_id"));
                            System.out.println("station:"+stations.get(i).getStationCode());
                            if((policeData.get("station_id").equals(stations.get(i).getStationCode()))){
                                //此站点有超标数据
                                System.out.println("ininini");
                                map.put("OverLimit","是");
                                break;
                            }else{
                                map.put("OverLimit","否");
                            }
                        }
                    }


                    Map<String,Object> mapGeometry=new HashMap<String,Object>();
                    mapGeometry.put("type","Point");
                    List<Float> coordinates=new ArrayList<>();
                    String[] coordinates_str=stations.get(i).getPosition().split(",");
                    Float coordinates_strlat=Float.parseFloat(coordinates_str[0]);
                    Float coordinates_strlon=Float.parseFloat(coordinates_str[1]);
                    coordinates.add(coordinates_strlon);
                    coordinates.add(coordinates_strlat);
                    mapGeometry.put("coordinates",coordinates);
                    map.put("geometry",mapGeometry);
                    lists.add(map);
                } else{
                    //此用户存在运维单位，需要数据权限
                    if(stations.get(i).getOperation_id()!=null){
                        if(stations.get(i).getOperation_id().equals(operation_id)){
                            Map<String,Object> map=new HashMap<String,Object>();
                            map.put("type","Feature");
                            map.put("id",stations.get(i).getStationCode());
                            map.put("name",stations.get(i).getStationName());
                            map.put("region",stations.get(i).getDistrict());
                            //map.put("OverLimit","否");
                            List<HData> hDatas= hDataDao.getLatestStationListByStationCode(stations.get(i).getStationCode());

                            if(hDatas!=null){
                                String time=(hDataDao.getLatestTimeByStationCode(stations.get(i).getStationCode().toString()));
                                if(time!=null){
                                    map.put("time",(time.substring(0,(time.length()-2))));
                                    for (int j= 0;j<hDatas.size();j++){
                                        if(hDatas.get(j).getNorm_code().equals(LeqAnorm_code)){
                                            map.put("LeqA",hDatas.get(j).getNorm_val());
                                        }
                                    }
                                }else{
                                    System.out.println("不存在最新数据");
                                    map.put("time",sdf.format(new Date()));
                                    map.put("LeqA","0");
                                }
                            }
                            List<String> MType_list=new ArrayList<String>();
                            MType_list.add("区域环境");
                            MType_list.add("功能区");
                            map.put("M_type",MType_list);

                            List<String> MType_value_list=new ArrayList<String>();
                            MType_value_list.add(""+stations.get(i).getArea());
                            MType_value_list.add(""+stations.get(i).getDomain());
                            map.put("M_typeValue",MType_value_list);

                            List<String> CType_list=new ArrayList<String>();
                            if(stations.get(i).getCountryCon()==1){
                                CType_list.add("国控");
                            }
                            if(stations.get(i).getCityCon()==1){
                                CType_list.add("市控");
                            }
                            if(stations.get(i).getDomainCon()==1){
                                CType_list.add("区控");
                            }
                            map.put("C_type",CType_list);
                            //待会修改

                            LogOffLine logOffLine=logOffLineDao.findByStationOrGatherID(stations.get(i).getStationCode());
                            if(logOffLine!=null){
                                if(logOffLine.getFlag()==1){
                                    map.put("S_type","在线");
                                }else if(logOffLine.getFlag()==0){
                                    map.put("S_type","离线");
                                }
                            }else{
                                map.put("S_type","在线");
                            }
                            if(stations.get(i).getStation_attribute()==1){
                                map.put("O_status","自动");
                            }else if(stations.get(i).getStation_attribute()==0){
                                map.put("O_status","手动");
                            }

                            if(policeDatas==null){
                                map.put("OverLimit","否");
                            }else{
                                //报警  是否超标
                                for (int p=0;p< policeDatas.size();p++) {
                                    Map<Object,Object> policeData= policeDatas.get(p);
                                    System.out.println(policeDatas.get(p));
                                    System.out.println((policeData.get("station_id").equals(stations.get(i).getStationCode())));
                                    System.out.println("police:"+policeData.get("station_id"));
                                    System.out.println("station:"+stations.get(i).getStationCode());
                                    if((policeData.get("station_id").equals(stations.get(i).getStationCode()))){
                                        //此站点有超标数据
                                        System.out.println("ininini");
                                        map.put("OverLimit","是");
                                        break;
                                    }else{
                                        map.put("OverLimit","否");
                                    }
                                }
                            }
                            Map<String,Object> mapGeometry=new HashMap<String,Object>();
                            mapGeometry.put("type","Point");
                            List<Float> coordinates=new ArrayList<>();
                            String[] coordinates_str=stations.get(i).getPosition().split(",");
                            Float coordinates_strlat=Float.parseFloat(coordinates_str[0]);
                            Float coordinates_strlon=Float.parseFloat(coordinates_str[1]);
                            coordinates.add(coordinates_strlon);
                            coordinates.add(coordinates_strlat);
                            mapGeometry.put("coordinates",coordinates);
                            map.put("geometry",mapGeometry);
                            lists.add(map);
                        }
                    }

                }
            }

            //gather
            List<Gather> gathers=gatherDao.findAll();
            if(gathers!=null){
                for(int i=0;i<gathers.size();i++){
                    if(operation_id.equals("0")){
                        Map<String,Object> map=new HashMap<String,Object>();
                        map.put("type","Feature");
                        map.put("id",gathers.get(i).getGather_id());
                        map.put("name",gathers.get(i).getGather_name());
                        map.put("region",gathers.get(i).getDistrict());
                        GatherData gatherDatas= gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id());
                        if(gatherDatas!=null){
                            String time = (gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id()).getData_time().toString());
                            if(time!=null){
                                if(gatherDatas.getNorm_code().equals(LeqAnorm_code)){
                                    map.put("LeqA",gatherDatas.getNorm_val());
                                }
                                map.put("time",(time.substring(0,(time.length()-2))));
                            }else{
                                System.out.println("不存在最新数据");
                                map.put("time",sdf.format(new Date()));
                                map.put("LeqA","0");
                            }
                            List<String> MType_list=new ArrayList<String>();
                            MType_list.add("区域环境");
                            MType_list.add("功能区");
                            map.put("M_type",MType_list);

                            List<String> MType_value_list=new ArrayList<String>();
                            MType_value_list.add(""+stations.get(i).getArea());
                            MType_value_list.add(""+stations.get(i).getDomain());
                            map.put("M_typeValue",MType_value_list);

                            List<String> CType_list=new ArrayList<String>();
                            if(gathers.get(i).getCountry_con()==1){
                                CType_list.add("国控");
                            }
                            if(gathers.get(i).getCity_con()==1){
                                CType_list.add("市控");
                            }
                            if(gathers.get(i).getDomain_con()==1){
                                CType_list.add("区控");
                            }
                            map.put("C_type",CType_list);

                            LogOffLine logOffLine=logOffLineDao.findByStationOrGatherID(gathers.get(i).getGather_code());
                            System.out.println("logoffline"+logOffLine);
                            if(logOffLine!=null){
                                if(logOffLine.getFlag()==1){
                                    map.put("S_type","在线");
                                }else if(logOffLine.getFlag()==0){
                                    map.put("S_type","离线");
                                }
                            }else{
                                map.put("S_type","在线");
                            }
                            map.put("O_status","流动");
                            //采集车没有报警
                            map.put("OverLimit","");
                            Map<String,Object> mapGeometry=new HashMap<String,Object>();
                            mapGeometry.put("type","Point");
                            List<Float> coordinates=new ArrayList<>();
                            String pos=gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id()).getGather_position();
                            String gatherposition=pos.substring(1, pos.length());
                            String[] coordinates_str=gatherposition.substring(0,gatherposition.length()-1).split(",");
                            Float coordinates_strlon=Float.parseFloat(coordinates_str[0]);
                            Float coordinates_strlat=Float.parseFloat(coordinates_str[1]);
                            coordinates.add(coordinates_strlon);
                            coordinates.add(coordinates_strlat);
                            mapGeometry.put("coordinates",coordinates);
                            map.put("geometry",mapGeometry);
                            lists.add(map);
                        }
                    }else{
                        if(gathers.get(i).getOperation_id()!=null){
                            if(gathers.get(i).getOperation_id().equals(operation_id)){
                                Map<String,Object> map=new HashMap<String,Object>();
                                map.put("type","Feature");
                                map.put("id",gathers.get(i).getGather_id());
                                map.put("name",gathers.get(i).getGather_name());
                                map.put("region",gathers.get(i).getDistrict());
                                GatherData gatherDatas= gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id());
                                if(gatherDatas!=null){
                                    //System.out.println("ss"+gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id()).getData_time().toString());
                                    String time = (gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id()).getData_time().toString());
                                    if(time!=null){
                                        if(gatherDatas.getNorm_code().equals(LeqAnorm_code)){
                                            map.put("LeqA",gatherDatas.getNorm_val());
                                        }
                                        map.put("time",(time.substring(0,(time.length()-2))));
                                    }else{
                                        System.out.println("不存在最新数据");
                                        map.put("time",sdf.format(new Date()));
                                        map.put("LeqA","0");
                                    }
                                    List<String> MType_list=new ArrayList<String>();
                                    MType_list.add("区域环境");
                                    MType_list.add("功能区");
                                    map.put("M_type",MType_list);

                                    List<String> MType_value_list=new ArrayList<String>();
                                    MType_value_list.add(""+stations.get(i).getArea());
                                    MType_value_list.add(""+stations.get(i).getDomain());
                                    map.put("M_typeValue",MType_value_list);

                                    List<String> CType_list=new ArrayList<String>();
                                    if(gathers.get(i).getCountry_con()==1){
                                        CType_list.add("国控");
                                    }
                                    if(gathers.get(i).getCity_con()==1){
                                        CType_list.add("市控");
                                    }
                                    if(gathers.get(i).getDomain_con()==1){
                                        CType_list.add("区控");
                                    }
                                    map.put("C_type",CType_list);

                                    LogOffLine logOffLine=logOffLineDao.findByStationOrGatherID(gathers.get(i).getGather_code());
                                    System.out.println("logoffline"+logOffLine);
                                    if(logOffLine!=null){
                                        if(logOffLine.getFlag()==1){
                                            map.put("S_type","在线");
                                        }else if(logOffLine.getFlag()==0){
                                            map.put("S_type","离线");
                                        }
                                    }else{
                                        map.put("S_type","在线");
                                    }
                                    map.put("O_status","流动");
                                    //采集车没有报警
                                    map.put("OverLimit","");
                                    Map<String,Object> mapGeometry=new HashMap<String,Object>();
                                    mapGeometry.put("type","Point");
                                    List<Float> coordinates=new ArrayList<>();
                                    String pos=gatherDataDao.getLaestDataByGather_id(gathers.get(i).getGather_id()).getGather_position();
                                    String gatherposition=pos.substring(1, pos.length());
                                    String[] coordinates_str=gatherposition.substring(0,gatherposition.length()-1).split(",");
                                    Float coordinates_strlat=Float.parseFloat(coordinates_str[0]);
                                    Float coordinates_strlon=Float.parseFloat(coordinates_str[1]);
                                    coordinates.add(coordinates_strlon);
                                    coordinates.add(coordinates_strlat);
                                    mapGeometry.put("coordinates",coordinates);
                                    map.put("geometry",mapGeometry);
                                    lists.add(map);
                                }else{//gather的某些站点没有数据，那么站点的很多数据都是空的,此站点数据返回空
                                    map.put("time",sdf.format(new Date()));
                                    System.out.println(new Date());
                                    map.put("LeqA","0");
                                    List<String> MType_list=new ArrayList<String>();
                                    MType_list.add("区域环境");
                                    MType_list.add("功能区");
                                    map.put("M_type",MType_list);

                                    List<String> MType_value_list=new ArrayList<String>();
                                    MType_value_list.add(""+stations.get(i).getArea());
                                    MType_value_list.add(""+stations.get(i).getDomain());
                                    map.put("M_typeValue",MType_value_list);

                                    List<String> CType_list=new ArrayList<String>();
                                    if(gathers.get(i).getCountry_con()==1){
                                        CType_list.add("国控");
                                    }
                                    if(gathers.get(i).getCity_con()==1){
                                        CType_list.add("市控");
                                    }
                                    if(gathers.get(i).getDomain_con()==1){
                                        CType_list.add("区控");
                                    }
                                    map.put("C_type",CType_list);
                                    LogOffLine logOffLine=logOffLineDao.findByStationOrGatherID(gathers.get(i).getGather_code());
                                    System.out.println("logoffline"+logOffLine);
                                    if(logOffLine!=null){
                                        if(logOffLine.getFlag()==1){
                                            map.put("S_type","在线");
                                        }else if(logOffLine.getFlag()==0){
                                            map.put("S_type","离线");
                                        }
                                    }else{
                                        map.put("S_type","在线");
                                    }
                                    map.put("O_status","流动");
                                    //采集车没有报警
                                    map.put("OverLimit","");
                                    Map<String,Object> mapGeometry=new HashMap<String,Object>();
                                    mapGeometry.put("type","Point");
                                    List<Object> coordinates=new ArrayList<>();
                                    coordinates.add("");
                                    coordinates.add("");
                                    mapGeometry.put("coordinates",coordinates);
                                    map.put("geometry",mapGeometry);
                                    lists.add(map);
                                }
                            }
                        }

                    }
                }
            }

        }

        resultMap.put("type","FeatureCollection");
        resultMap.put("features",lists);
        System.out.println(lists.size());
        return resultMap;
    }

    @Override
    public List<Station> queryStationsByCodeLikeAndArea(String area, String query) {
        List<Station> stations = stationDao.findByStationCodeNameLikeAndArea(area,query);
        return stations;
    }

    @Override
    public List<Station> queryStationsByNameLikeAndArea(String area, String query) {
        List<Station> stations = stationDao.findByStationCodeNameLikeAndArea(area,query);
        return stations;
    }

    @Override
    public void updateStationOperation(String operation_id,String station_code){
        stationDao.updateStationOperation(operation_id,station_code);
    }

    @Override
    public List<Station> findByOperationId(String operatationId) {
        return stationDao.findByOperationId(operatationId);
    }


    @Override
    public List<Station> queryStationsByDistrictAndDomain(String district, int Domain) {
        List<Station> stations = stationDao.findByDistrictAndDomain(district, Domain);
        return stations;
    }

    @Override
    public List<Station> getOperationStationLike(String district,String operation_id,String key){
        return stationDao.getOperationStationLike(district,operation_id,key);
    }

    @Override
    public List<Station> getOperationStationLikeAll(String operation_id,String key){
        return stationDao.getOperationStationLikeAll(operation_id,key);
    }

    @Override
    public String findStationNameByStationId(String station_id) {
        return stationDao.findStationNameByStationId(station_id);
    }
}
