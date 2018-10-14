package com.springboot.environment.service;

import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Role;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
public interface RoleService {

    List<Role> getAll();

    void addOne(Role role);

    void delOne(String role_id);

    void updateOne(String role_id, String role_name);
}
