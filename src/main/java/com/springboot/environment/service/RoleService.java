package com.springboot.environment.service;

import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by yww on 2018/9/11.
 */
public interface RoleService {

    List< Object[]> getAll();

    void addOne(Role role);

    void delOne(String role_id);

    void updateOne(String role_id, String role_name);

    List<Role> getRoleByUserID(String user_id);

    Map addRole(Map params);

    Map rolePermissionDistribute(Map params);

    Map deleteRole(Map params);

    Map queryRole();

    Map queryRoleByRoleID(Map params);

    Map updateMenulistAndPermissionList(Map params);
}
