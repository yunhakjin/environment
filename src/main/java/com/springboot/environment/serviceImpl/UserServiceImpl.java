package com.springboot.environment.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.springboot.environment.bean.Gather;
import com.springboot.environment.bean.Role;
import com.springboot.environment.bean.Station;
import com.springboot.environment.bean.User;
import com.springboot.environment.dao.GatherDao;
import com.springboot.environment.dao.RoleDao;
import com.springboot.environment.dao.StationDao;
import com.springboot.environment.dao.UserDao;
import com.springboot.environment.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.models.auth.In;
import org.apache.catalina.connector.Request;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StationDao stationDao;

    @Autowired
    private GatherDao gatherDao;



    @Autowired
    private RedisTemplate redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public JSONObject getUser(String loginName, String password) {
        return null;
    }

    @Override
    public User findByUserId(String user_id) {
        return userDao.loadByUserId((user_id));
        // return null;
    }

    @Transactional
    @Override
    public Boolean register(String user_id, String name, String password, String mail , String tel, String user_prefer,String role_id,String group_id, HttpSession session, HttpServletRequest request) {

        ByteSource salt = ByteSource.Util.bytes(user_id);
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
        User u = new User();
        u.setUser_id((user_id));
        u.setPassword(newPs);
        u.setUser_mail(mail);
        u.setUser_tel(tel);
        u.setUser_name(name);
        u.setUser_prefer(user_prefer);
        userDao.save(u);
        userDao.saveUser_Role(user_id,Integer.parseInt(role_id));
        userDao.saveUser_Group(user_id,Integer.parseInt(group_id));
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
    public List< Object[]> getAll() {
        return userDao.getAll();
    }


    @Override
    public void delOne(String user_id) {
        userDao.deleteUserRole((user_id));
        userDao.deleteUserGroup((user_id));
        userDao.deleteById(Integer.parseInt(user_id));
    }

    @Override
    public void updateOne(String user_id, String user_name, String password, String user_mail, String user_tel, String user_prefer) {
        userDao.updateOne((user_id),user_name,password,user_mail,user_tel,user_prefer);
    }

    @Override
    public List<String> getPrefer(String user_id) {
        String[] prefers=userDao.loadByUserId((user_id)).getUser_prefer().split(",");
        List<String> lists=new ArrayList<>();
        for (String a: prefers) {
            lists.add(a);
        }
        return lists;
    }

    @Override
    public Map getLikeUserIDandName(Map params) {
        //String  keyPoint="{\"key\":\"31010702330051\"}";
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        String key=(String)params.get("key");
        List<Map> userList = new ArrayList<Map>();
        List<User> users_LikeNameAndID=userDao.getUserByLikeNameAndID(key);
        for (int i=0;i<users_LikeNameAndID.size();i++){
            Map<String,String> userMap=new LinkedHashMap<String,String>();
            userMap.put("user_id",users_LikeNameAndID.get(i).getUser_id().toString());
            userMap.put("user_name",users_LikeNameAndID.get(i).getUser_name().toString());
            userList.add(userMap);
        }
        resultMap.put("userList",userList);
        return resultMap;
    }

    @Override
    public Map getUserByID(Map params) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        String selectedUserId=params.get("selectedUserId")+"";
        System.out.println(selectedUserId);
        List<Map> userList = new ArrayList<Map>();
        if(selectedUserId.equals("*")){
            List<User> users=userDao.findAll();
            for (int i=0;i<users.size();i++){
                Map<String,Object> userMap=new LinkedHashMap<String,Object>();
                System.out.println("dddddd"+userDao.getRoleIDByUserID(users.get(i).getUser_id()));
                if(userDao.getRoleIDByUserID(users.get(i).getUser_id())!=null){
                    String role_id=userDao.getRoleIDByUserID(users.get(i).getUser_id());
                    Role role = roleDao.getOne(Integer.parseInt(role_id));
                    System.out.println(role_id);
                    userMap.put("user_id",users.get(i).getUser_id().toString());
                    userMap.put("user_name",users.get(i).getUser_name().toString());
                    userMap.put("user_tel",users.get(i).getUser_tel().toString());
                    userMap.put("user_mail",users.get(i).getUser_mail().toString());
                    userMap.put("password",users.get(i).getPassword().toString());
                    userMap.put("frozenflag",users.get(i).getFrozenflag());
                    userMap.put("operation_id",users.get(i).getOperation_id());
                    userMap.put("role_id",role.getRole_id());
                    userMap.put("role_name",role.getRole_name());
                }else{
                    userMap.put("user_id",users.get(i).getUser_id().toString());
                    userMap.put("user_name",users.get(i).getUser_name().toString());
                    userMap.put("user_tel",users.get(i).getUser_tel().toString());
                    userMap.put("user_mail",users.get(i).getUser_mail().toString());
                    userMap.put("password",users.get(i).getPassword().toString());
                    userMap.put("frozenflag",users.get(i).getFrozenflag());
                    userMap.put("operation_id",users.get(i).getOperation_id());
                }
                userList.add(userMap);

            }
        }else {
            User user=userDao.loadByUserId((selectedUserId));
            System.out.println(user);
            Map<String,Object> userMap=new LinkedHashMap<String,Object>();
            if(userDao.getRoleIDByUserID(user.getUser_id())!=null){
                String role_id=userDao.getRoleIDByUserID(user.getUser_id());
                Role role = roleDao.getOne(Integer.parseInt(role_id));
                userMap.put("user_id",user.getUser_id().toString());
                userMap.put("user_name",user.getUser_name().toString());
                userMap.put("user_tel",user.getUser_tel().toString());
                userMap.put("user_mail",user.getUser_mail().toString());
                userMap.put("password",user.getPassword().toString());
                userMap.put("role_id",role.getRole_id());
                userMap.put("role_name",role.getRole_name());
                userMap.put("frozenflag",user.getFrozenflag());
                userMap.put("operation_id",user.getOperation_id());
            }else {
                userMap.put("user_id",user.getUser_id().toString());
                userMap.put("user_name",user.getUser_name().toString());
                userMap.put("user_tel",user.getUser_tel().toString());
                userMap.put("user_mail",user.getUser_mail().toString());
                userMap.put("password",user.getPassword().toString());
                userMap.put("frozenflag",user.getFrozenflag());
                userMap.put("operation_id",user.getOperation_id());
            }
            userList.add(userMap);
            System.out.println(userMap);
        }
        resultMap.put("userInfoData",userList);
        return resultMap;
    }

    @Override
    public Map addUser(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        String type=(String)params.get("type");
        String user_id=(String)params.get("user_id");
        String user_name=(String)params.get("user_name");
        String password=(String)params.get("password");
        String role=(String)params.get("role");
        ByteSource salt = ByteSource.Util.bytes(user_id);
        String newPs = new SimpleHash("MD5", password, salt, 1024).toHex();
        String user_tel=(String)params.get("user_tel");
        String user_mail=(String)params.get("user_mail");
        String operation_id=(String)params.get("operation_id");
        String frozenflag=(String)params.get("frozenflag");

        User user=new User();
        user.setUser_id((user_id));
        user.setUser_name(user_name);
        user.setPassword(newPs);
        user.setUser_tel(user_tel);
        user.setUser_mail(user_mail);
        user.setOperation_id(operation_id);
        user.setFrozenflag(Integer.parseInt(frozenflag));
        if(type.equals("add")){
            user.setPassword(newPs);
            User u=userDao.loadByUserId((user_id));
            if(u==null){
                userDao.save(user);
                userDao.addRoleUser((user_id),Integer.parseInt(role));
                User user1=userDao.loadByUserId((user_id));
                if(user1!=null){
                    resultMap.put("addFlag","true");
                }else{
                    resultMap.put("addFlag","false");
                }
            }else{
                System.out.println("已经存在此用户");
                resultMap.put("addFlag","false");
            }

        }else if(type.equals("edit")){
            //判断user-id是否有，如果有，那么

            user.setPassword(password);
            User u=userDao.loadByUserId((user_id));
            if(u!=null){
                userDao.save(user);
                User user1=userDao.loadByUserId((user_id));
                if(user1.getUser_name().equals(user_name)&&user1.getPassword().equals(password)&&user1.getUser_mail().equals(user_mail)&&user1.getUser_tel().equals(user_tel)){
                    resultMap.put("editFlag","true");
                }else{
                    resultMap.put("editFlag","false");
                }
               int userRoleCount=userDao.Count((user_id));
               System.out.println(userRoleCount+"userRoleCount");
               if(userRoleCount==1){
                   userDao.updateRoleUser((user_id),Integer.parseInt(role));
               }else {
                   userDao.addRoleUser((user_id),Integer.parseInt(role));

               }

            }else{
                System.out.println("不存在此用户");
                resultMap.put("editFlag","false");
            }
        }else if(type.equals("updatePWD")){
            //判断user-id是否有，如果有，那么
            user.setPassword(newPs);
            User u=userDao.loadByUserId((user_id));
            if(u!=null){
                userDao.save(user);
                User user1=userDao.loadByUserId((user_id));
                if(user1.getUser_name().equals(user_name)&&user1.getPassword().equals(newPs)&&user1.getUser_mail().equals(user_mail)&&user1.getUser_tel().equals(user_tel)){
                    resultMap.put("updateFlag","true");
                }else{
                    resultMap.put("updateFlag","false");
                }
                int userRoleCount=userDao.Count((user_id));
                System.out.println(userRoleCount+"userRoleCount");
                if(userRoleCount==1){
                    userDao.updateRoleUser((user_id),Integer.parseInt(role));
                }else {
                    userDao.addRoleUser((user_id),Integer.parseInt(role));
                }
            }else{
                System.out.println("不存在此用户");
                resultMap.put("updateFlag","false");
            }
        }
        return resultMap;
    }

    @Override
    public Map deleteUser(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        List<String> userIdList=(List)params.get("deleteUserList");
        int count=0;
        for(int i=0;i<userIdList.size();i++){
            String user_id=userIdList.get(i).toString();
            //判断用户和其他的表连接关系-如果存在就删除--最后再删除用户信息。user_group user-role
            //暂时不考虑，后期考虑
            if(userDao.loadByUserId((user_id))!=null){
                List<Object[]> UserRoleList=userDao.getUserRoleByUserID(Integer.parseInt(user_id));
                if(UserRoleList!=null){
                    System.out.println("UserRoleList"+UserRoleList);
                }
                userDao.deleteById(Integer.parseInt(user_id));
                if (userDao.loadByUserId((user_id))==null){
                    count++;
                }
            }else{
                resultMap.put("deleteFlag","false");
            }

        }
        if(count==userIdList.size()){
            resultMap.put("deleteFlag","true");
        }else{
            resultMap.put("deleteFlag","false");
        }
        return resultMap;
    }

    @Override
    public Map pwdVerification(Map params) {
        String user_id=(String)params.get("user_id");
        String old_pwd=(String)params.get("old_pwd");
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        User user=userDao.loadByUserId((user_id));
        ByteSource salt = ByteSource.Util.bytes(user_id);
        String newPs = new SimpleHash("MD5", old_pwd, salt, 1024).toHex();
        if(newPs.equals(user.getPassword())){
            resultMap.put("flag","true");
        }else{
            resultMap.put("flag","false");
        }
        return resultMap;
    }

    @Override
    public Map frozenOrActiveUser(Map params) {
        String user_id=(String)params.get("user_id");
        boolean froze=(boolean)params.get("froze");
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        User user=userDao.loadByUserId((user_id));
        if(user!=null){
            if(froze==false&&user.getFrozenflag()==0){
                int flag=userDao.updateActiveFlag(user_id);
                if(flag==1){
                    resultMap.put("activeFlag","true");
                }else{
                    resultMap.put("activeFlag","false");
                }
            }else if(froze==true&&user.getFrozenflag()==1){
                int flag=userDao.updateFrozenFlag(user_id);
                if(flag==1){
                    resultMap.put("frozeFlag","true");
                }else{
                    resultMap.put("frozeFlag","false");
                }
            }
        }else{
            System.out.println("此用户不存在");
        }

        return resultMap;
    }



}
