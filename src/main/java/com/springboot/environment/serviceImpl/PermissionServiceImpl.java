package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Permission;
import com.springboot.environment.dao.PermissionDao;
import com.springboot.environment.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/10/13.
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public List< Object[]> getAll() {
        return permissionDao.getAll();
    }

    @Override
    public void addOne(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public void updateOne(String permission_id, String permission_name) {
        permissionDao.updateOne(Integer.parseInt(permission_id));
    }

    @Override
    public void delOne(String permission_id) {
        permissionDao.deletePermissionRole(Integer.parseInt(permission_id));
        permissionDao.deleteById(Integer.parseInt(permission_id));
    }
}
