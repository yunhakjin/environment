package com.springboot.environment.dao;

import com.springboot.environment.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Query(value = "select DISTINCT * from role",nativeQuery = true)
    List<Object[]> getAll();

    @Query(value = "select * from role where role_id in (select role_id from user_role where user_id=?1)",nativeQuery = true)
    List<Role> getRoleByUserID(Integer user_id);


    @Transactional
    @Modifying
    @Query(value = "insert into role(role_name) values(?1)",nativeQuery = true)
    int addOne( String role_name);

    @Transactional
    @Modifying
    @Query(value = "update role set  permission_list = ?2 where role_id= ?1",nativeQuery = true)
    int updatePermission(String role_id, String role_permissions);

    @Transactional
    @Modifying
    @Query(value = "delete from role where role_id= ?1",nativeQuery = true)
    int deleteOne(int i);


    @Query(value = "select * from role",nativeQuery = true)
    List<Role> getAllRoles();
}