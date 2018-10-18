package com.springboot.environment.dao;

import com.springboot.environment.bean.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/10/13.
 */
public interface PermissionDao  extends JpaRepository<Permission,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update permission set permission_name=?2 where permission_id=?1 ",nativeQuery = true)
    void updateOne(int permission_id);

    @Transactional
    @Modifying
    @Query(value = "delete from  role_permission where permission_id = ?1",nativeQuery = true)
    void deletePermissionRole(int permission_id);

    @Query(value = "select DISTINCT * from permission",nativeQuery = true)
    List<Object[]> getAll();
}
