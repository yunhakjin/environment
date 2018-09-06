package com.springboot.environment.dao;

import com.springboot.environment.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yww on 2018/9/2.
 */
public interface RoleDao extends JpaRepository<Role,Integer> {
}
