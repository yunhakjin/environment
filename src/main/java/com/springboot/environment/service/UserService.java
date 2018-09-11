package com.springboot.environment.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by yww on 2018/9/11.
 */
public interface UserService {
    JSONObject getUser(String loginName, String password);

    User findByUsername(String username);

    Boolean register(String name, String password, String mail, String tel, HttpSession session, HttpServletRequest request);

    User getOne(int i);

    Boolean testRedisSet();


    // Object login(String name, String pass, HttpSession session, HttpServletRequest request);

}
