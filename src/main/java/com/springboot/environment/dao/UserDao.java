package com.springboot.environment.dao;

import com.springboot.environment.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
public interface UserDao extends JpaRepository<User,Integer> {


    @Query(value = "select DISTINCT * from user u where u.user_id = ?1",nativeQuery = true)
    User loadByUserId(@Param("user_id")String user_id);

    @Transactional
    @Modifying
    @Query(value = "insert into user_role(user_id,role_id) values(?1,?2)",nativeQuery = true)
    int saveUser_Role(String user_id, int role_id);

    @Transactional
    @Modifying
    @Query(value = "insert into user_group(user_id,group_id) values(?1,?2)",nativeQuery = true)
    int saveUser_Group(String user_id, int group_id);

    @Transactional
    @Modifying
    @Query(value = "delete from  user_role where user_id = ?1",nativeQuery = true)
    void deleteUserRole(String user_id);

    @Transactional
    @Modifying
    @Query(value = "delete from  user_group where user_id = ?1",nativeQuery = true)
    void deleteUserGroup(String user_id);

    @Transactional
    @Modifying
    @Query(value = "update user set user_name=?2,password=?3,user_mail=?4,user_tel=?5,user_prefer=?6 where user_id=?1 ",nativeQuery = true)
    void updateOne(String user_id, String user_name, String password, String user_mail, String user_tel, String user_prefer);

    @Query(value = "select DISTINCT * from user",nativeQuery = true)
    List<Object[]> getAll();


    @Query(value = "select * from user u where u.user_id like %?1% or u.user_name like %?1% ",nativeQuery = true)
    List<User> getUserByLikeNameAndID(String key);

    @Query(value = "select * from user_role where user_id = ?1 ",nativeQuery = true)
    List<Object[] > getUserRoleByUserID(int i);

    @Transactional
    @Modifying
    @Query(value = "insert into user_role(user_id,role_id) values(?1,?2)",nativeQuery = true)
    int addRoleUser(String i, int i1);

    @Transactional
    @Modifying
    @Query(value = "update user_role set role_id =?2 where user_id=?1 ",nativeQuery = true)
    void updateRoleUser(String i, int i1);

    @Query(value = "select count(*) from user_role where user_id = ?1 ",nativeQuery = true)
    int Count(String i);

    @Query(value = "select role_id from user_role where user_id = ?1 ",nativeQuery = true)
    String getRoleIDByUserID(String user_id);

    @Transactional
    @Modifying
    @Query(value = "update user set frozenflag=1 where user_id=?1 ",nativeQuery = true)
    int updateActiveFlag(String user_id);

    @Transactional
    @Modifying
    @Query(value = "update user set frozenflag=0 where user_id=?1 ",nativeQuery = true)
    int updateFrozenFlag(String user_id);
}