package com.springboot.environment.dao;


import com.springboot.environment.bean.Gather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
public interface GatherDao extends JpaRepository<Gather,Integer> {
    @Query(value = "select distinct * from gather where gather_id=?1",nativeQuery = true)
    public Gather getAllByGather_id(String gather_id);

    @Query(value="select distinct * from gather where operation_id=?1",nativeQuery = true)
    public List<Gather> getGatherByOperation_id(String operation_id);

    @Transactional
    @Modifying
    @Query(value="insert into gather(application,area,city_con,country_con,district,domain,domain_con,gather_code," +
                  "gather_id,gather_id_dz,gather_name,gather_status,online_flag,protocol,protocol_name,street,gather_major,"+
                  "gather_setup,gather_setupdate,company_code,climate,radar,operation_id) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12," +
                  "?13,?14,?15,?16,?17,?18,?19,?20,?21,?22,?23)",nativeQuery = true)
    void insertGather(String application,int area,int city_con,int country_con,String district,int domain,int domain_con,
                      String gather_code,String gather_id,String gather_id_dz,String gather_name,int gather_status,
                      int online_flag,int protocol,String protocol_name,String street,String gather_major,String gather_setup,
                      String gather_setupdate,String company_code,int climate,int radar,String operation_id);

    @Transactional
    @Modifying
    @Query(value="delete from gather where gather_id=?1",nativeQuery = true)
    void deleteGather(String gather_id);

    @Transactional
    @Modifying
    @Query(value="update gather set area=?1,application=?2,city_con=?3,country_con=?4,district=?5," +
            "domain=?6,domain_con=?7,gather_code=?8,gather_id=?9,gather_id_dz=?10,gather_name=?11 " +
            ",gather_status=?12,online_flag=?13,protocol=?14,protocol_name=?15,street=?16,gather_major=?17" +
            ",gather_setup=?18,gather_setupdate=?19,company_code=?20,climate=?21,radar=?22,operation_id=?23 where gather_id=?24",nativeQuery = true)
    void updateGather(int area,String application,int city_con,int country_con,String district,int domain,int domain_con,
                      String gather_code,String gather_id,String gather_id_dz,String gather_name,int gather_status,
                      int online_flag,int protocol,String protocol_name,String street,String gather_major,String gather_setup,
                      String gather_setupdate,String company_code,int climate,int radar,String operation_id,String target);

    /**
     * 修改某一个采集车的运维单位*/
    @Transactional
    @Modifying
    @Query(value = "update gather set operation_id=?1 where gather_id=?2",nativeQuery = true)
    void updateGatherOperation(String operation_id,String gather);

    @Query(value = "select * from gather s where operation_id = ?1", nativeQuery = true)
    List<Gather> findByOperationId(String operatationId);
}
