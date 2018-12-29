package com.springboot.environment.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;

/*采集车实体类*/
@Data
@Entity
@Table(name = "gather")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//采集车类
//id	主键	int(6)	自增
//gather_id	采集车编号	varchar(20)	对内采集车编号
//gather_code	采集车标号	varchar(20)	对外采集车编号
//gather_name	采集车名称	varchar(50)
//gather_status	采集车状态	int(1)	run/disable 运营/停运    1/0
//application	所属应用	varchar(50)	暂未使用
//online_flag	在线标识	int(1)	断线/在线    0/1
//gather_id_dz	对照码	varchar(50)	设备发送数据中带的mn码
//protocol	所用协议	int(1)	老协议/新协议 1/2
//protocol_name	所用协议名称	varchar(20)
//street	站点所属街道	varchar(30)	例如：长风街道 类型为：枚举
//district	站点所属行政区	varchar(10)	例如：长宁区
//country_con	国控	int(1)	是/否   1/0
//city_con	市控	int(1)	是/否   1/0
//domain_con	区控	int(1)	是/否  1/0
//area	区域环境	int(1)	类型为：枚举 0/1/2/3…
//domain	功能区	int(1)	类型为：枚举 0/1/2/3…
public class Gather {
    @Id
    @GeneratedValue
    private Integer id;

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Column(name = "gather_id",length = 20)
    private String gather_id;

    public void setGather_id(String gather_id) {
        this.gather_id = gather_id;
    }

    public String getGather_id() {
        return gather_id;
    }

    @Column(name = "gather_code",length = 20)
    private  String gather_code;

    public void setGather_code(String gather_code) {
        this.gather_code = gather_code;
    }

    public String getGather_code() {
        return gather_code;
    }

    @Column(name = "gather_name",length = 50)
    private String gather_name;
    public void setGather_name(String gather_name) {
        this.gather_name = gather_name;
    }

    public String getGather_name() {
        return gather_name;
    }

    @Column(name="gather_status",length = 1)
    private  int gather_status;

    public void setGather_status(int gather_status) {
        this.gather_status = gather_status;
    }

    public int getGather_status() {
        return gather_status;
    }

    @Column(name = "application",length = 50)
    private  String application;

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplication() {
        return application;
    }

    @Column(name="online_flag",length = 1)
    private int online_flag;

    public void setOnline_flag(int online_flag) {
        this.online_flag = online_flag;
    }

    public int getOnline_flag() {
        return online_flag;
    }

    @Column(name = "gather_id_dz",length = 50)
    private String gather_id_dz;

    public void setGather_id_dz(String gather_id_dz) {
        this.gather_id_dz = gather_id_dz;
    }

    public String getGather_id_dz() {
        return gather_id_dz;
    }

    @Column(name = "protocol",length = 1)
    private int protocol;

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getProtocol() {
        return protocol;
    }

    @Column(name = "protocol_name",length = 20)
    private  String protocol_name;

    public void setProtocol_name(String protocol_name) {
        this.protocol_name = protocol_name;
    }

    public String getProtocol_name() {
        return protocol_name;
    }


    @Column(name = "street",length = 30)
    private String street;

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    @Column(name = "district",length = 10)
    private String district;

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    @Column(name = "country_con",length = 1)
    private int country_con;

    public void setCountry_con(int country_con) {
        this.country_con = country_con;
    }

    public int getCountry_con() {
        return country_con;
    }

    @Column(name = "city_con",length = 1)
    private int city_con;

    public void setCity_con(int city_con) {
        this.city_con = city_con;
    }

    public int getCity_con() {
        return city_con;
    }

    @Column(name = "domain_con",length = 1)
    private int domain_con;

    public void setDomain_con(int domain_con) {
        this.domain_con = domain_con;
    }

    public int getDomain_con() {
        return domain_con;
    }

    @Column(name = "area",length = 1)
    private  int area;

    public void setArea(int area) {
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    @Column(name = "domain",length = 1)
    private int domain;

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public int getDomain() {
        return domain;
    }

    /**
     *主管部门
     */
    @Column(name = "gather_major")
    private String gather_major;

    public void setGather_major(String gather_major) {
        this.gather_major = gather_major;
    }

    public String getGather_major() {
        return gather_major;
    }

    /**
     *建站单位
     */
    @Column(name = "gather_setup")
    private String gather_setup;

    public void setGather_setup(String gather_setup) {
        this.gather_setup = gather_setup;
    }

    public String getGather_setup() {
        return gather_setup;
    }

    /**
     *建站时间
     */
    @Column(name = "gather_setupdate")
    private Date gather_setupdate;

    public void setGather_setupdate(Date gather_setupdate) {
        this.gather_setupdate = gather_setupdate;
    }

    public Date getGather_setupdate() {
        return gather_setupdate;
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
     * 运维单位*/
    @Column(name = "operation_id")
    private String operation_id;

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getOperation_id() {
        return operation_id;
    }

    @Override
    public String toString() {
        return "Gather{" +
                "id=" + id +
                ", gather_id='" + gather_id + '\'' +
                ", gather_code='" + gather_code + '\'' +
                ", gather_name='" + gather_name + '\'' +
                ", gather_status=" + gather_status +
                ", application='" + application + '\'' +
                ", online_flag=" + online_flag +
                ", gather_id_dz='" + gather_id_dz + '\'' +
                ", protocol=" + protocol +
                ", protocol_name='" + protocol_name + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", country_con=" + country_con +
                ", city_con=" + city_con +
                ", domain_con=" + domain_con +
                ", area=" + area +
                ", domain=" + domain +
                ", gather_major='" + gather_major + '\'' +
                ", gather_setup='" + gather_setup + '\'' +
                ", gather_setupdate=" + gather_setupdate +
                ", company_code='" + company_code + '\'' +
                ", climate=" + climate +
                ", radar=" + radar +
                ", operation_id='" + operation_id + '\'' +
                '}';
    }
}
