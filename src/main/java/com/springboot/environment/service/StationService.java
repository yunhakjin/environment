package com.springboot.environment.service;

import com.springboot.environment.bean.Station;

import java.util.List;

public interface StationService {

    List<Station> queryStationsByCountryCon(int isCountryCon);
}
