package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站点表实体类
 */
@Data
@Entity
@Table(name = "station")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 站点的主键id，没有实际的意义
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;
    /**
     * 对内站点编号
     */
    @Column(name = "STATION_ID")
    private String stationId;
    /**
     * 对外站点编号
     */
    @Column(name = "STATION_CODE")
    private String stationCode;
    /**
     * 站点简称
     * */
    @Column(name="station_sim")
    private String stationSim;
    /**
     * 站点名称
     */
    @Column(name = "STATION_NAME")
    private String stationName;
    /**
     * 站点状态
     * 0 停运 1 运营
     */
    @Column(name = "STATION_STATUS")
    private int stationStatus;
    /**
     * 所属应用(暂未使用)
     */
    @Column(name = "APPLICATION")
    private String application;
    /**
     * 在线标识
     * 0 短线 1 在线
     */
    @Column(name = "ONLINE_FLAG")
    private int onlineFlag;
    /**
     * 对照码
     */
    @Column(name = "STATION_ID_DZ")
    private String stationIdDZ;
    /**
     * 所用协议
     * 1 老协议 2 新协议
     */
    @Column(name = "PROTOCOL")
    private int protocol;
    /**
     * 所用协议名称
     */
    @Column(name = "PROTOCOL_NAME")
    private String protocolName;
    /**
     * 站点位置
     */
    @Column(name = "station_position")
    private String position;
    /**
     * 站点所属街道
     */
    @Column(name = "STREET")
    private String street;
    /**
     * 站点所属行政区
     */
    @Column(name = "DISTRICT")
    private String district;
    /**
     * 噪声点范围
     */
    @Column(name = "station_range")
    private String range;
    /**
     * 国控
     * 0 否 1 是
     */
    @Column(name = "COUNTRY_CON")
    private int countryCon;
    /**
     * 市控
     * 0 否 1 是
     */
    @Column(name = "CITY_CON")
    private int cityCon;
    /**
     * 区控
     * 0 否 1 是
     */
    @Column(name = "DOMAIN_CON")
    private int domainCon;
    /**
     * 区域环境
     */
    @Column(name = "AREA")
    private int area;
    /**
     * 功能区
     */
    @Column(name = "DOMAIN")
    private int domain;

    /**
     * 站点性质
     * 1 自动 0 手工
     */
    @Column(name = "station_attribute")
    private int station_attribute;

    public void setStation_attribute(int station_attribute) {
        this.station_attribute = station_attribute;
    }

    public int getStation_attribute() {
        return station_attribute;
    }

    /**
     *主管部门
     */
    @Column(name = "station_major")
    private String station_major;

    public void setStation_major(String station_major) {
        this.station_major = station_major;
    }

    public String getStation_major() {
        return station_major;
    }

    /**
     *建站单位
     */
    @Column(name = "station_setup")
    private String station_setup;

    public void setStation_setup(String station_setup) {
        this.station_setup = station_setup;
    }

    public String getStation_setup() {
        return station_setup;
    }

    /**
     *建站时间
     */
    @Column(name = "station_setupdate")
    private Date station_setupdate;

    public void setStation_setupdate(Date station_setupdate) {
        this.station_setupdate = station_setupdate;
    }

    public Date getStation_setupdate() {
        return station_setupdate;
    }

    /**
     *企业代码
     */
    @Column(name = "company_code")
    private String company_code;

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getCompany_code() {
        return company_code;
    }

    /**
     *是否有气象数据:1有,0无 */
    @Column(name = "climate")
    private int climate;

    public void setClimate(int climate) {
        this.climate = climate;
    }

    public int getClimate() {
        return climate;
    }

    /**
     * 是否有雷达数据:1有,0无*/
    @Column(name="radar")
    private int radar;

    public void setRadar(int radar) {
        this.radar = radar;
    }

    public int getRadar() {
        return radar;
    }

    /**
     * 运维单位编号*/
    @Column(name = "operation_id")
    private String operation_id;

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void setStationSim(String stationSim) {
        this.stationSim = stationSim;
    }

    public String getStationSim() {
        return stationSim;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", stationId='" + stationId + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationStatus=" + stationStatus +
                ", application='" + application + '\'' +
                ", onlineFlag=" + onlineFlag +
                ", stationIdDZ='" + stationIdDZ + '\'' +
                ", protocol=" + protocol +
                ", protocolName='" + protocolName + '\'' +
                ", station_position='" + position + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", station_range='" + range + '\'' +
                ", countryCon=" + countryCon +
                ", cityCon=" + cityCon +
                ", domainCon=" + domainCon +
                ", area=" + area +
                ", domain=" + domain +
                '}';
    }
}
