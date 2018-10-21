package com.springboot.environment.util;

/**
 * 站点信息相关常量
 */
public class StationConstant {

    //站点运行状态常量
    public static final int STATION_RUN = 1;
    public static final int STATION_DISABLE = 0;
    public static final String RUN_STR= "1";
    public static final String DISABLE_STR = "0";

    //站点在线标识
    public static final int STATION_ONLINE = 1;
    public static final int STATION_OFFLINE = 0;
    public static final String ONLINE_STR = "1";
    public static final String OFFLINE_STR = "0";

    //站点所用新老协议
    public static final int STATION_OLD_PROTOCOL = 1;
    public static final int STATION_NEW_PROTOCOL = 2;
    public static final String OLD_PROTOCOL_STR = "1";
    public static final String NEW_PROTOCOL_STR = "2";

    //国控
    public static final int STATION_IS_COUNTRY_CON = 1;
    public static final int STATION_ISNOT_COUNTRY_CON = 0;
    public static final String IS_COUNTRY_CON_STR = "1";
    public static final String ISNOT_COUNTRY_CON_STR = "0";

    //市控
    public static final int STATION_IS_CITY_CON = 1;
    public static final int STATION_ISNOT_CITY_CON = 0;
    public static final String IS_CITY_CON_STR = "1";
    public static final String ISNOT_CITY_CON_STR = "0";

    //区控
    public static final int  STATION_IS_DOMAIN_CON = 1;
    public static final int  STATION_ISNOT_DOMAIN_CON = 0;
    public static final String IS_DOMAIN_CON_STR = "1";
    public static final String ISNOT_DOMAIN_CON_STR = "0";
}
