package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.User;
import com.springboot.environment.dao.UserDao;
import com.springboot.environment.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public JSONObject getUser(String loginName, String password) {
        return null;
    }

    @Override
    public User findByUserId(String user_id) {
        return userDao.loadByUserId(Integer.parseInt(user_id));
        // return null;
    }

    @Transactional
    @Override
    public Boolean register(String user_id, String name, String password, String mail , String tel, String user_prefer,String role_id,String group_id, HttpSession session, HttpServletRequest request) {

        ByteSource salt = ByteSource.Util.bytes(name);
        /*
        * MD5加密：
        * 使用SimpleHash类对原始密码进行加密。
        * 第一个参数代表使用MD5方式加密
        * 第二个参数为原始密码
        * 第三个参数为盐值，即用户名
        * 第四个参数为加密次数
        * 最后用toHex()方法将加密后的密码转成String
        * */
        String newPs = new SimpleHash("MD5", password, salt, 1024).toHex();
        boolean flag=false;
        User u = new User();
        u.setUser_id(Integer.parseInt(user_id));
        u.setPassword(newPs);
        u.setUser_mail(mail);
        u.setUser_tel(tel);
        u.setUser_name(name);
        u.setUser_prefer(user_prefer);
        userDao.save(u);
        userDao.saveUser_Role(Integer.parseInt(user_id),Integer.parseInt(role_id));
        userDao.saveUser_Group(Integer.parseInt(user_id),Integer.parseInt(group_id));
        return true;
    }

    @Override
    public User getOne(int id) {
        User user=(User) redisTemplate.opsForValue().get(id);
        if(user==null){
            user=userDao.getOne(id);
            redisTemplate.opsForValue().set(id,user);
        }
        return user;
    }

    @Override
    public Boolean testRedisSet() {
        boolean flag=false;
        redisTemplate.opsForValue().set("0906","yww");
        if(redisTemplate.opsForValue().get("0906")!=null){
            logger.info("get 0906="+redisTemplate.opsForValue().get("0906"));
            flag=true;
        }
        return flag;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }


    @Override
    public void delOne(String user_id) {
        userDao.deleteUserRole(Integer.parseInt(user_id));
        userDao.deleteUserGroup(Integer.parseInt(user_id));
        userDao.deleteById(Integer.parseInt(user_id));
    }

    @Override
    public void updateOne(String user_id, String user_name, String password, String user_mail, String user_tel, String user_prefer) {
        userDao.updateOne(Integer.parseInt(user_id),user_name,password,user_mail,user_tel,user_prefer);
    }


}
