package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.environment.bean.*;
import com.springboot.environment.dao.*;
import com.springboot.environment.repositoiry.HDataRepositority;
import com.springboot.environment.service.DDataService;
import com.springboot.environment.util.DateUtil;
import com.springboot.environment.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DDataServiceImp implements DDataService {

    @Autowired
    private DDataDao dDataDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private NormDao normDao;

    @Autowired
    ThresholdDao thresholdDao;

    @Autowired
    HDataRepositority hDataRepositority;

    private static final String LEQ = "LEQ";

    @Override
    public List<DData> getAll() {
        return dDataDao.findAll();
    }

    @Override
    public List<DData> getDDataByData_id(String Data_id) {
          return dDataDao.getAllByData_id(Data_id);
    }

    @Override
    public void saveDData(DData dData) {
        dDataDao.saveAndFlush(dData);
    }

    @Override
    public void delDData(String Data_id) {
        dDataDao.deleteAll(getDDataByData_id(Data_id));
    }

    @Override
    public List<DData> getByStationAndDate(String station_id, String date) {
        return null;
    }

    @Override
    public String queryDdataByStationIdAndDatetime(String stationId, String date, int offset) {

        //需要查找的月份
        String dateTime = DateUtil.getDdateTimeByOffset(date, offset);
        //月初开始时间
        String monthBeginTime = DateUtil.getMonthFirstDay(dateTime);
        //月末结束时间
        String monthEndTime = DateUtil.getMonthEndDay(dateTime);
        //需要查询的小时数据的开始时间，如果是10月，那么小时数据的开始时间是9月30日6点，日期格式没有秒
        String hDataBeginTime = DateUtil.getLastMonthSixClock(monthBeginTime);
        //需要查询的小时数据的结束时间,如果是10月，那么结束时间是10月31日6点，日期格式没有秒
        String hDataEndTime = DateUtil.getMonthDayEndTimeSixClock(dateTime);

        //查询当月的天数
        int dayNums = DateUtil.getDayNumOfMonth(dateTime);
        List<DData> dDatas = dDataDao.queryDdataByStationIdAndTime(stationId, monthBeginTime, monthEndTime);

        JSONArray ddataArray = new JSONArray();
        JSONObject ddataJSON = new JSONObject();
        JSONObject dataJSON = new JSONObject();
        //如果指定时间内有数据
        if (!StringUtil.isNullOrEmpty(dDatas)){
            //构造空的内容
            for (int i = 1; i <= dayNums; i++) {
                JSONObject object = new JSONObject();
                object.put("time", i);
                ddataArray.add(object);
            }

            //日数据按照日期分组
            Map<Date, List<DData>> map = Maps.newTreeMap();
            for (DData dData : dDatas) {
                if (map.containsKey(dData.getData_time())) {
                    map.get(dData.getData_time()).add(dData);
                } else {
                    List<DData> dDataList = Lists.newArrayList();
                    dDataList.add(dData);
                    map.put(dData.getData_time(), dDataList);
                }
            }

            //将日数据的指标插入对应日期
            for (List<DData> dDataList : map.values()) {
                //当前时间的天数
                int currDate = DateUtil.getDayOfThisDate(dDataList.get(0).getData_time());
                JSONObject object = ddataArray.getJSONObject(currDate -1);
                for (DData dData : dDataList) {
                    if (dData.getNorm_code() != null && dData.getNorm_val() != null) {
                        object.put(dData.getNorm_code(), dData.getNorm_val());
                    }
                    //此数据是LD值，需要显示有效率
                    if (dData.getNorm_code().equals("n00008")){
                        object.put("effective_rate_ld", StringUtil.convertStringToInt(dData.getNorm_vdr()));
                    }
                    //此数据是LN值，需要显示有效率
                    if (dData.getNorm_code().equals("n00009")){
                        object.put("effective_rate_ln", StringUtil.convertStringToInt(dData.getNorm_vdr()));
                    }
                    //如果此数值的Ldn值，需要显示有效率
                    if (dData.getNorm_code().equals("n00007")){
                        object.put("effective_rate_ldn", StringUtil.convertStringToInt(dData.getNorm_vdr()));
                    }
                }
            }
            //构造日数据的其他信息(ld ln的最大值，达标率，超标率)
            //查询出来一个月的所有小时数据
            System.out.println(hDataBeginTime);
            System.out.println(hDataEndTime);
            //查询出当月的所有LEQ指标
            Norm norm = normDao.getNormCodeByNorimIdCode(LEQ);
            List<HData> hdataList = hDataRepositority.getNormHdataByStationIdAndTime(stationId, hDataBeginTime, hDataEndTime, norm.getNorm_code());

            if (!StringUtil.isNullOrEmpty(hdataList)) {
                //查询该站点的昼夜阈值
                Threshold threshold = thresholdDao.getThresholdByStationCode(stationId);
                float ldLimit = Float.parseFloat(threshold.getD_limit());
                float lnLimit = Float.parseFloat(threshold.getN_limit());
                //将LEQ指标根据日期和昼夜构造map
                Map<String, List<HData>> leqMap = new HashMap<>();
                for (HData hData : hdataList) {
                    int day = DateUtil.getDayOfThisDate(hData.getData_time());
                    int hour = DateUtil.gethourOfThisDate(hData.getData_time());
                    if (hour >=6 && hour <= 22) {
                        //map的key : 日期+day
                        if (!leqMap.containsKey((DateUtil.getDayAfterThisDay(hData.getData_time())) + "day")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put((DateUtil.getDayAfterThisDay(hData.getData_time())) + "day", list);
                        }
                        leqMap.get((DateUtil.getDayAfterThisDay(hData.getData_time())) + "day").add(hData);
                        if (!leqMap.containsKey(DateUtil.getDayAfterThisDay(hData.getData_time()) +"")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put(DateUtil.getDayAfterThisDay(hData.getData_time()) + "", list);
                        }
                        leqMap.get(DateUtil.getDayAfterThisDay(hData.getData_time()) + "").add(hData);
                    }
                    //下一天的夜数据
                    else if (hour > 22 && hour <= 23){
                        if (!leqMap.containsKey((DateUtil.getDayAfterThisDay(hData.getData_time())) + "night")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put((DateUtil.getDayAfterThisDay(hData.getData_time())) + "night", list);
                        }
                        leqMap.get((DateUtil.getDayAfterThisDay(hData.getData_time())) + "night").add(hData);
                        if (!leqMap.containsKey(DateUtil.getDayAfterThisDay(hData.getData_time()) +"")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put(DateUtil.getDayAfterThisDay(hData.getData_time()) + "", list);
                        }
                        leqMap.get(DateUtil.getDayAfterThisDay(hData.getData_time()) + "").add(hData);
                    }
                    //当天的夜数据
                    else if (hour >= 0 && hour < 6){
                        if (!leqMap.containsKey(day + "night")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put(day + "night", list);
                        }
                        leqMap.get(day + "night").add(hData);
                        if (!leqMap.containsKey(day + "")){
                            List<HData> list = new ArrayList<>();
                            leqMap.put(day + "", list);
                        }
                        leqMap.get(day + "").add(hData);
                    }
                }
                //构造当天数据
                for (int i = 0; i < ddataArray.size(); i++) {
                    JSONObject object = ddataArray.getJSONObject(i);
                    int currDay = object.getInteger("time");
                    //全天leq的最大值
                    if (leqMap.containsKey(currDay + "")){
                        List<HData> totalData = leqMap.get(currDay + "");
                        String max_ldn = getMaxLEQInList(totalData);
                        object.put("max_ldn", max_ldn);
                    }
                    if (leqMap.containsKey(currDay + "day")) {
                        List<HData> dayData = leqMap.get(currDay + "day");
                        String max_ld_day = getMaxLEQInList(dayData);
                        object.put("max_ld_day", max_ld_day);
                        int overRate = computeOverRate(dayData, ldLimit);
                        int qualifiedRate = 100 - overRate;
                        object.put("overRate_day", overRate + "%");
                        object.put("qualifiedRate_day", qualifiedRate + "%");
                    }
                    if (leqMap.containsKey(currDay + "night")) {
                        List<HData> nightData = leqMap.get(currDay + "night");
                        int overRate = computeOverRate(nightData, lnLimit);
                        int qualifiedRate = 100 - overRate;
                        object.put("overRate_night", overRate + "%");
                        object.put("qualifiedRate_night", qualifiedRate + "%");
                    }
                }
                //将当前时间的多余数据删除，判断当前的年月是否是给定的年月
                Date nowDate = new Date();
                if (DateUtil.isCurrYearAndMonth(nowDate, dateTime)) {
                    int currDate = DateUtil.getDayNowDate(nowDate);
                    for (int i = currDate + 1; i <= dayNums; i++) {
                        JSONObject newObject = new JSONObject();
                        newObject.put("time", i);
                        ddataArray.set(i - 1, newObject);
                    }
                }

            }
            dataJSON.put("count", dayNums);
            dataJSON.put("data", ddataArray);
            dataJSON.put("time", dateTime);
            ddataJSON.put("siteData", dataJSON);

            System.out.println(ddataJSON.toJSONString());
            return ddataJSON.toJSONString();
        }
        else {
            dataJSON.put("count", 0);
            dataJSON.put("data", "");
            dataJSON.put("time", dateTime);
            ddataJSON.put("siteData", dataJSON);

            System.out.println(ddataJSON.toJSONString());
            return ddataJSON.toJSONString();
        }
    }

    @Override
    public List<DData> getByStationAndMonth(String station_id, String month) {
        return dDataDao.getByStationAndMonth(station_id,month);
    }

    @Override
    public List<DData> getByStationAndDay(String station_id, String date) {
        return dDataDao.getByStationAndDate(station_id,date);
    }


      /*
       * 月数据查询：返回所查的单站点两个月份的三十天的指标对比
       * */
    @Override
    public Map getStationsData(Map<String, Object> params) {
        //String stationRequest = "{station_id:\"31010702330055\", type:2,  timeRange:{ time1:\"2018-08\",   time2:\"2018-09\"   }}";
        String s=params+"";
        System.out.println(s);

        Map<String,String> timeRange=(Map<String, String>) params.get("timeRange");
        String station_id=params.get("station_id")+"";
        String type=params.get("type")+"";

        String time1=timeRange.get("time1")+"";
        String time2=timeRange.get("time2")+"";


        String year1=time1.split("-")[0];
        String month1=time1.split("-")[1];

        String year2=time2.split("-")[0];
        String month2=time2.split("-")[1];

        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        System.out.println(station_id);
        Station station=stationDao.findStationByStationId(station_id);
        System.out.println(station);
        String station_name= station.getStationName();

        List<DData> dDatas_time1=dDataDao.getByStationAndMonth(station_id,time1);
        List<DData> dDatas_time2=dDataDao.getByStationAndMonth(station_id,time2);
        System.out.println(dDatas_time1);
        Map<String, Object> time1Map = new LinkedHashMap<String, Object>();//time1
        Map<String, Object> time2Map = new LinkedHashMap<String, Object>();//time2
        time1Map.put("time",time1);
        time2Map.put("time",time2);
        SimpleDateFormat sdf=new SimpleDateFormat("dd");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf3=new SimpleDateFormat("MM");

        Map<String,Map> innertrackMap=new HashMap<String,Map>();
        Map<String,Map> innertrack2Map=new HashMap<String,Map>();
        for(int i=0;i<dDatas_time1.size();i++){
            //String trackTime=dDatas_time1.get(i).getData_time().toString().substring(0,10);
            String trackTime=sdf.format(dDatas_time1.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrackMap.containsKey(trackTime)){
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",sdf2.format(dDatas_time1.get(i).getData_time()));
                System.out.println(dDatas_time1.get(i).getData_time());
                System.out.println(sdf2.format(dDatas_time1.get(i).getData_time()));
                normVal.put(dDatas_time1.get(i).getNorm_code(),dDatas_time1.get(i).getNorm_val());
                innertrackMap.put(trackTime,normVal);
            }
        }
        for(int i=0;i<dDatas_time2.size();i++){
            //String trackTime=dDatas_time2.get(i).getData_time().toString().substring(0,10);
            String trackTime=sdf.format(dDatas_time2.get(i).getData_time());
            Map<String,String> normVal=new HashMap<String,String>();
            if(innertrack2Map.containsKey(trackTime)){
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.get(trackTime).putAll(normVal);
            }
            else{
                normVal.put("time",sdf2.format(dDatas_time2.get(i).getData_time()));
                System.out.println(dDatas_time2.get(i).getData_time());
                System.out.println(sdf2.format(dDatas_time2.get(i).getData_time()));
                normVal.put(dDatas_time2.get(i).getNorm_code(),dDatas_time2.get(i).getNorm_val());
                innertrack2Map.put(trackTime,normVal);
            }
        }
        System.out.println(innertrack2Map);
        List<Map> innerTrackList=new ArrayList<Map>();
        List<Map> innerTrackList2=new ArrayList<Map>();

        List<Norm> normList1=normDao.getAllByDflag();
        List<Norm> normList2=normDao.getAllByDflag();
        if(month1.equals("02")){
            if(Integer.valueOf(year1)%4==0){
                for(int i=1;i<10;i++){
                    if(!innertrackMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+"0"+i+"");
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<30;i++){
                    if(!innertrackMap.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put(String.valueOf(i),map);
                    }
                }
            }
            else{
                for(int i=1;i<10;i++){
                    if(!innertrackMap.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+"0"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put("0"+i,map);
                    }
                }
                for(int i=10;i<29;i++){
                    if(!innertrackMap.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year1+"-"+month1+"-"+i);
                        for(Norm norm:normList1){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrackMap.put(String.valueOf(i),map);
                    }
                }
            }
        }
        else if(month1.equals("01")||month1.equals("03")||month1.equals("05")||month1.equals("07")||month1.equals("08")||month1.equals("10")||month1.equals("12")){
            for(int i=1;i<10;i++){
                if(!innertrackMap.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+"0"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put("0"+i,map);
                }
            }
            for(int i=10;i<32;i++){
                if(!innertrackMap.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put(String.valueOf(i),map);
                }
            }
        }
        else{
            for(int i=1;i<10;i++){
                if(!innertrackMap.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+"0"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put("0"+i,map);
                }
            }
            for(int i=10;i<31;i++){
                if(!innertrackMap.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year1+"-"+month1+"-"+i);
                    for(Norm norm:normList1){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrackMap.put(String.valueOf(i),map);
                }
            }
        }
//=======================================================================
//
//    @Override
//    public String queryDdataByStationIdAndDatetime(String stationId, String date) {
//        return null;
//    }

        if(month2.equals("02")){
            if(Integer.valueOf(year2)%4==0){
                for(int i=1;i<10;i++){
                    if(!innertrack2Map.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+"0"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put("0"+i,map);
                    }
                }
                for(int i=10;i<30;i++){
                    if(!innertrack2Map.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put(String.valueOf(i),map);
                    }
                }
            }
            else{
                for(int i=1;i<10;i++){
                    if(!innertrack2Map.containsKey("0"+i)){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+"0"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put("0"+i,map);
                    }
                }
                for(int i=10;i<29;i++){
                    if(!innertrack2Map.containsKey(i+"")){
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("time",year2+"-"+month2+"-"+i);
                        for(Norm norm:normList2){
                            map.put(norm.getNorm_code(),"");
                        }
                        innertrack2Map.put(String.valueOf(i),map);
                    }
                }
            }
        }
        else if(month2.equals("01")||month2.equals("03")||month2.equals("05")||month2.equals("07")||month2.equals("08")||month2.equals("10")||month2.equals("12")){
            for(int i=1;i<10;i++){
                if(!innertrack2Map.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+"0"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put("0"+i,map);
                }
            }
            for(int i=10;i<32;i++){
                if(!innertrack2Map.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put(String.valueOf(i),map);
                }
            }
        }
        else{
            for(int i=1;i<10;i++){
                if(!innertrack2Map.containsKey("0"+i)){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+"0"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put("0"+i,map);
                }
            }
            for(int i=10;i<31;i++){
                if(!innertrack2Map.containsKey(i+"")){
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("time",year2+"-"+month2+"-"+i);
                    for(Norm norm:normList2){
                        map.put(norm.getNorm_code(),"");
                    }
                    innertrack2Map.put(String.valueOf(i),map);
                }
            }
        }



        for(Map value:innertrackMap.values()){

            innerTrackList.add(value);

        }
        for(Map value:innertrack2Map.values()){
            innerTrackList2.add(value);
        }


        Collections.sort(innerTrackList, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String map1value =o1.get("time")+"";
                String map2value =o2.get("time")+"";
                return map1value.compareTo(map2value);
            }
        });
        Collections.sort(innerTrackList2, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String map1value =o1.get("time")+"";
                String map2value =o2.get("time")+"";
                return map1value.compareTo(map2value);
            }
        });
        int count=innerTrackList.size()+innerTrackList2.size();
        resultMap.put("count", count);
        resultMap.put("station_id",station_id);
        resultMap.put("station_name",station_name);

        time1Map.put("data",innerTrackList);
        time2Map.put("data",innerTrackList2);
        resultMap.put("time1",time1Map);
        resultMap.put("time2",time2Map);

        return resultMap;
    }

    /**
     * 得到一个list中的指标最小值
     * @param list
     * @return
     */
    private String getMaxLEQInList(List<HData> list) {
        if (list == null) {
            return "";
        }
        float max = Float.MIN_VALUE;
        for (HData hData : list){
            float curr = Float.parseFloat(hData.getNorm_val());
            max = curr > max ? curr : max;
        }
        return max + "";
    }

    private int computeOverRate(List<HData> list, float limit) {
        int overCount = 0;
        for (HData hData : list){
            float currValue = Float.parseFloat(hData.getNorm_val());
            if (currValue >= limit){
                overCount ++;
            }
        }
        return overCount / list.size() * 100;
    }
}
