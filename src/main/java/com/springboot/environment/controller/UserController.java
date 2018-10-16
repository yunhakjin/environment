package com.springboot.environment.controller;

/**
 * Created by yww on 2018/10/13.
 */

import com.springboot.environment.bean.User;
import com.springboot.environment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("用户类相关api")
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;


    @ApiOperation(value="注册",notes = "注册包括用户个人信息：id,name,pwd,mail,tel,prefer,role_id,group_id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String"),
            @ApiImplicitParam(name = "user_name",value="用户名",dataType = "String"),
            @ApiImplicitParam(name="password",value = "用户密码",dataType = "String"),
            @ApiImplicitParam(name = "user_mail",value = "用户邮箱",dataType = "String"),
            @ApiImplicitParam(name="user_tel",value = "电话号码",dataType = "String"),
            @ApiImplicitParam(name="user_prefer",value = "用户喜好",dataType = "String",example = "A,B,C"),
            @ApiImplicitParam(name="role_id",value = "角色id",dataType = "String"),
            @ApiImplicitParam(name="group_id",value = "用户组id",dataType = "String")
    }
    )
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    @ResponseBody
    public Boolean register(String user_id,String user_name, String password, String user_mail , String user_tel, String user_prefer,String role_id,String group_id,HttpSession session, HttpServletRequest request) {
        return userService.register(user_id, user_name, password, user_mail, user_tel, user_prefer, role_id, group_id, session, request);
    }

    @ApiOperation(value="通过用户id加载用户信息",notes = "需要参数：用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String")
    }
    )
    @RequestMapping(value = "/loadByUserId/{user_id}")
    @ResponseBody
    public User loadByUserId(@PathVariable String user_id) {
        return userService.findByUserId(user_id);
    }

    @ApiOperation(value="登录",notes = "需要参数：用户id，用户密码,session")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String"),
            @ApiImplicitParam(name="password",value = "用户密码",dataType = "String"),
            @ApiImplicitParam(name="session",value = "session",dataType = "HttpSession")
    }
    )
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
   /* @GetMapping("/testRedis")
    public User findOne(){
        return userService.getOne(1);
    }

    @GetMapping("/testRedisSet")
    public Boolean testRedisSet(){
        return userService.testRedisSet();
    }*/

    @ApiOperation(value="返回所有用户的信息",notes = "所有用户的信息")
    @GetMapping(value = "/getall")
    public List<User> getAll(){
        return userService.getAll();
    }



    @ApiOperation(value="修改某一个用户",notes = "需要用户的所有信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String"),
            @ApiImplicitParam(name = "user_name",value="用户名",dataType = "String"),
            @ApiImplicitParam(name="password",value = "用户密码",dataType = "String"),
            @ApiImplicitParam(name = "user_mail",value = "用户邮箱",dataType = "String"),
            @ApiImplicitParam(name="user_tel",value = "电话号码",dataType = "String"),
            @ApiImplicitParam(name="user_prefer",value = "用户喜好",dataType = "String",example = "A,B,C")
    }
    )
    @RequestMapping(value = "/updateone/{user_id}/{user_name}/{password}/{user_mail}/{user_tel}/{user_prefer}",method=RequestMethod.GET)
    @ResponseBody
    public void updateOne(@PathVariable String user_id,
                          @PathVariable String user_name,
                          @PathVariable String password,
                          @PathVariable String user_mail,
                          @PathVariable String user_tel,
                          @PathVariable String user_prefer){
        userService.updateOne(user_id,user_name,password,user_mail,user_tel,user_prefer);
    }

    @ApiOperation(value="删除某一个用户",notes = "根据用户id删除，同时删除与role，group的连接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String")
    }
    )
    @RequestMapping(value="/deleteone/{user_id}",method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteOne(@PathVariable String user_id){
        userService.delOne(user_id);
        return true;
    }

    @ApiOperation(value="得到用户喜好列表",notes = "根据用户id获得用户喜好列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value="用户id（指定）",dataType = "String")
    }
    )
    @RequestMapping(value="/getprefer/{user_id}",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getPrefer(@PathVariable String user_id){
        return  userService.getPrefer(user_id);
    }


}
