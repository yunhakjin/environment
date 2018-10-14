package com.springboot.environment.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
public interface UserService {

    JSONObject getUser(String loginName, String password);

    User findByUserId(String user_id);

    Boolean register(String user_id, String name, String password, String mail , String tel, String user_prefer,String role_id,String group_id, HttpSession session, HttpServletRequest request);

    User getOne(int i);

    Boolean testRedisSet();

    List<User> getAll();

     

    void delOne(String user_id);

    void updateOne(String user_id, String user_name, String password, String user_mail, String user_tel, String user_prefer);


    // Object login(String name, String pass, HttpSession session, HttpServletRequest request);

}
