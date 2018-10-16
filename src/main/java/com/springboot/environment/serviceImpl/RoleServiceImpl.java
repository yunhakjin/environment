package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Role;
import com.springboot.environment.dao.RoleDao;
import com.springboot.environment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    @Override
    public void addOne(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delOne(String role_id) {
        roleDao.deleteRoleUser(Integer.parseInt(role_id));
        roleDao.deleteRolePermission(Integer.parseInt(role_id));
        roleDao.deleteById(Integer.parseInt(role_id));
    }

    @Override
    public void updateOne(String role_id, String role_name) {
        roleDao.updateOne(Integer.parseInt(role_id),role_name);
    }
}
