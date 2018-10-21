package com.springboot.environment.service;

import com.springboot.environment.bean.Permission;

import java.util.List;

/**
 * Created by yww on 2018/10/13.
 */
public interface PermissionService {
    List< Object[]> getAll();

    void addOne(Permission permission);

    void updateOne(String permission_id, String permission_name);

    void delOne(String permission_id);
}
