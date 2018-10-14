package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Group;
import com.springboot.environment.dao.GroupDao;
import com.springboot.environment.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<Group> getAll() {
        return groupDao.findAll();
    }

    @Override
    public void addOne(Group group) {
        groupDao.save(group);
    }

    @Override
    public void updateOne(String group_id, String group_name) {
        groupDao.updateOne(Integer.parseInt(group_id),group_name);
    }

    @Override
    public void delOne(String group_id) {
        groupDao.deleteGroupUser(Integer.parseInt(group_id));
        groupDao.deleteById(Integer.parseInt(group_id));
    }
}
