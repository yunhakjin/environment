package com.springboot.environment.dao;

import com.springboot.environment.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yww on 2018/9/2.
 */
public interface UserDao extends JpaRepository<User,Integer> {


     @Query(value = "select DISTINCT * from euser u where u.user_name = ?1",nativeQuery = true)
     User loadByUserName(@Param("user_name")String user_name);
}
