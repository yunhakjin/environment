package com.springboot.environment.bean;

/**
 * 站点表实体类
 */
public class Station {

    /**
     * 对内站点编号
     */
    private String stationId;
    /**
     * 对外站点编号
     */
    private String stationCode;
    /**
     * 站点名称
     */
    private String stationName;
    /**
     * 站点状态
     * 0 停运 1 运营
     */
    private int stationStatus;
    /**
     * 所属应用(暂未使用)
     */
    private String application;
    /**
     * 在线标识
     * 0 短线 1 在线
     */
    private int onlineFlag;
    /**
     * 对照码
     */
    private String stationIdDZ;
    /**
     * 所用协议
     * 1 老协议 2 新协议
     */
    private int protocol;
    /**
     * 所用协议名称
     */
    private String protocolName;
    /**
     * 站点位置
     */
    private String position;
    /**
     * 站点所属街道
     */
    private String street;
    /**
     * 站点所属行政区
     */
    private String district;
    /**
     * 噪声点范围
     */
    private String range;
    /**
     * 国控
     * 0 否 1 是
     */
    private int countryCon;
    /**
     * 市控
     * 0 否 1 是
     */
    private int cityCon;
    /**
     * 区控
     * 0 否 1 是
     */
    private int domainCon;
    /**
     * 区域环境
     */
    private int area;
    /**
     * 功能区
     */
    private int domain;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(int stationStatus) {
        this.stationStatus = stationStatus;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public int getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(int onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public String getStationIdDZ() {
        return stationIdDZ;
    }

    public void setStationIdDZ(String stationIdDZ) {
        this.stationIdDZ = stationIdDZ;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getCountryCon() {
        return countryCon;
    }

    public void setCountryCon(int countryCon) {
        this.countryCon = countryCon;
    }

    public int getCityCon() {
        return cityCon;
    }

    public void setCityCon(int cityCon) {
        this.cityCon = cityCon;
    }

    public int getDomainCon() {
        return domainCon;
    }

    public void setDomainCon(int domainCon) {
        this.domainCon = domainCon;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationId='" + stationId + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationStatus=" + stationStatus +
                ", application='" + application + '\'' +
                ", onlineFlag=" + onlineFlag +
                ", stationIdDZ='" + stationIdDZ + '\'' +
                ", protocol=" + protocol +
                ", protocolName='" + protocolName + '\'' +
                ", position='" + position + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", range='" + range + '\'' +
                ", countryCon=" + countryCon +
                ", cityCon=" + cityCon +
                ", domainCon=" + domainCon +
                ", area=" + area +
                ", domain=" + domain +
                '}';
    }
}
