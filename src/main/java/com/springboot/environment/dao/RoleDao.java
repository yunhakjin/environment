package com.springboot.environment.dao;

import com.springboot.environment.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yww on 2018/9/11.
 */
public interface RoleDao extends JpaRepository<Role,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update role set role_name=?2 where role_id=?1 ",nativeQuery = true)
    void updateOne(int role_id, String role_name);

    @Transactional
    @Modifying
    @Query(value = "delete from  user_role where role_id = ?1",nativeQuery = true)
    void deleteRoleUser(int role_id);

    @Transactional
    @Modifying
    @Query(value = "delete from  role_permission where role_id = ?1",nativeQuery = true)
    void deleteRolePermission(int role_id);

}