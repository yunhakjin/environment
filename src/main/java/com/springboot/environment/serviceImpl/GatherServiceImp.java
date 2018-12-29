package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Gather;
import com.springboot.environment.dao.GatherDao;
import com.springboot.environment.service.GatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatherServiceImp implements GatherService {
    @Autowired
    private GatherDao gatherDao;

    @Override
    public List<Gather> getAllGather() {
        return gatherDao.findAll();
    }

    @Override
    public Gather getOneGather(String gather_id) {
        return gatherDao.getAllByGather_id(gather_id);
    }

    @Override
    public void insertGather(Gather gather,String setupdate) {
        String application=gather.getApplication();
        int area=gather.getArea();
        int city_con=gather.getCity_con();
        int country_con=gather.getCountry_con();
        String district=gather.getDistrict();
        int domain=gather.getDomain();
        int domain_con=gather.getDomain_con();
        String gather_code=gather.getGather_code();
        String gather_id=gather.getGather_id();
        String gather_id_dz=gather.getGather_id_dz();
        String gather_name=gather.getGather_name();
        int gather_status=gather.getGather_status();
        int online_flag=gather.getOnline_flag();
        int protocol=gather.getProtocol();
        String protocol_name=gather.getProtocol_name();
        String street=gather.getStreet();
        String gather_major=gather.getGather_major();
        String gather_setup=gather.getGather_setup();
        String gather_setupdate=setupdate;
        String company_code=gather.getCompany_code();
        int climate=gather.getClimate();
        int radar=gather.getRadar();
        String operation_id=gather.getOperation_id();
        if(gather_setupdate==""||gather_setupdate==null)
            gather_setupdate="1000-01-01 00:00:00";
        gatherDao.insertGather(application,area,city_con,country_con,district,domain,domain_con,gather_code,
                gather_id,gather_id_dz,gather_name,gather_status,online_flag,protocol,protocol_name,street,gather_major,
                        gather_setup,gather_setupdate,company_code,climate,radar,operation_id);
    }

    @Override
    public void deleteGather(String gather_id) {
        gatherDao.deleteGather(gather_id);
    }

    @Override
    public void updateGather(Gather gather, String setupdate, String target) {
        String application=gather.getApplication();
        int area=gather.getArea();
        int city_con=gather.getCity_con();
        int country_con=gather.getCountry_con();
        String district=gather.getDistrict();
        int domain=gather.getDomain();
        int domain_con=gather.getDomain_con();
        String gather_code=gather.getGather_code();
        String gather_id=gather.getGather_id();
        String gather_id_dz=gather.getGather_id_dz();
        String gather_name=gather.getGather_name();
        int gather_status=gather.getGather_status();
        int online_flag=gather.getOnline_flag();
        int protocol=gather.getProtocol();
        String protocol_name=gather.getProtocol_name();
        String street=gather.getStreet();
        String gather_major=gather.getGather_major();
        String gather_setup=gather.getGather_setup();
        String gather_setupdate=setupdate;
        String company_code=gather.getCompany_code();
        int climate=gather.getClimate();
        int radar=gather.getRadar();
        String operation_id=gather.getOperation_id();
        if(gather_setupdate==""||gather_setupdate==null)
            gather_setupdate="1000-01-01 00:00:00";
        gatherDao.updateGather(area,application,city_con,country_con,district,domain,domain_con,gather_code,
                gather_id,gather_id_dz,gather_name,gather_status,online_flag,protocol,protocol_name,street,gather_major,
                gather_setup,gather_setupdate,company_code,climate,radar,operation_id,target);
    }

    @Override
    public void updateGatherOperation(String operation_id,String gather_id){
        gatherDao.updateGatherOperation(operation_id,gather_id);
    }

    @Override
    public List<Gather> getGatherByOperation_id(String operation_id){
        return gatherDao.getGatherByOperation_id(operation_id);}

    @Override
    public List<Gather> findByOperationId(String operationId) {
        return gatherDao.findByOperationId(operationId);

    }
}
