package com.springboot.environment.config;

import com.springboot.environment.bean.Group;
import com.springboot.environment.bean.Role;
import com.springboot.environment.bean.User;
import com.springboot.environment.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yww on 2018/9/2.
 */
public class AuthRealm extends AuthorizingRealm {
    /**
     * 日志打印
     */
    private final static Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    @Autowired
    UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Session session = SecurityUtils.getSubject().getSession();
        //查询用户的权限
       // JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
    //    logger.info("permission的值为:" + permission);//输出cmd
    //    logger.info("本用户权限为:" + permission.get("permissionList"));
        //为当前用户设置角色和权限
        logger.info("session:" + session.getId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
       // authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
        User user  = (User)principalCollection.getPrimaryPrincipal();
        for(Role role:user.getRoles()){
            authorizationInfo.addRole(role.getRole_name());
        }
        for(Group p:user.getGroups()){
            authorizationInfo.addStringPermission(p.getGroup_name());
        }
        logger.info("本用户权限为:" + authorizationInfo.getRoles());
        return authorizationInfo;//把权限放入shiro中
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
  /*      String loginName = (String) authenticationToken.getPrincipal();
        // 获取用户密码
        String password = new String((char[]) authenticationToken.getCredentials());
        JSONObject user = userServiceImpl.getUser(loginName, password);
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getString("username"),
                user.getString("password"),
                //ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
                getName()
        );
        //session中不需要保存密码
        user.remove("password");
        //将用户信息放入session中，放入shiro调用CredentialsMatcher检验密码
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
       */
//        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
//        //获取用户的输入的账号.
//        String username = (String)authenticationToken.getPrincipal();
//        System.out.println(authenticationToken.getCredentials());
//        //通过username从数据库中查找 User对象，如果找到，没找到.
//        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        User user = userService.findByUsername(username);
//        System.out.println("----->>userInfo="+user);
//        if(user == null){
//            return null;
//        }
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                user, //用户名
//                user.getPassword(), //密码
//               // ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
//                getName()  //realm name
//        );

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取前端输入的用户名
        String userName  = usernamePasswordToken.getUsername();
        System.out.println("NAME"+userName);
        //根据用户名查询数据库中对应的记录
        User user = userService.findByUsername(userName);
        System.out.println(user);
        //当前realm对象的name
        String realmName = getName();
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUser_name());
        //封装用户信息，构建AuthenticationInfo对象并返回
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(),credentialsSalt, realmName);
        return authcInfo;


    }
}
