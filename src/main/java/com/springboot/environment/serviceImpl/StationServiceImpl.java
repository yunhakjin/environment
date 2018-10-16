package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Station;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.service.StationService;
import com.springboot.environment.util.StationConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class StationServiceImpl implements StationService {

    @Resource
    StationDao stationDao;



    @Override
    public List<Station> queryStationsByCountryCon(int isCountryCon) {

        if (isCountryCon != StationConstant.STATION_IS_COUNTRY_CON && isCountryCon != StationConstant.STATION_ISNOT_COUNTRY_CON){
           return null;
        }

        List<Station> stations = stationDao.findByCountryCon(isCountryCon);
        return stations;
    }
}
