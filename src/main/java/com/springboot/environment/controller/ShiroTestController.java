package com.springboot.environment.controller;

import com.springboot.environment.bean.User;
import com.springboot.environment.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yww on 2018/9/11.
 */
@RestController
@RequestMapping(value = "ShiroTest")
public class ShiroTestController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/register",method = RequestMethod.GET)
    @ResponseBody
    public Boolean register(String user_id,String user_name, String password, String user_mail , String user_tel, String user_prefer,String role_id,String group_id,HttpSession session, HttpServletRequest request) {
        return userService.register(user_id, user_name, password, user_mail, user_tel, user_prefer, role_id, group_id, session, request);
    }

    @RequestMapping(value = "/loadByUserId",method = RequestMethod.GET)
    @ResponseBody
    public User loadByUserId(String user_id) {
        return userService.findByUserId(user_id);
    }


    @RequestMapping(value="/login",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> submitLogin(String user_id, String password, HttpSession session) {
        System.out.println(user_id);
        System.out.println(password);
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user_id,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);   //完成登录
            User user=(User) subject.getPrincipal();
            session.setAttribute("user", user);
            System.out.println(user);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");
            resultMap.put("user", user.getUser_id());
            resultMap.put("password",user.getPassword());
        }catch(Exception e) {
            resultMap.put("status", 500);
            e.printStackTrace();
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    @GetMapping("/testRedis")
    public User findOne(){
        return userService.getOne(1);
    }

    @GetMapping("/testRedisSet")
    public Boolean testRedisSet(){
        return userService.testRedisSet();
    }


}

