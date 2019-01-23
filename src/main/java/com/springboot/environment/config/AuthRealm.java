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
 * Created by yww on 2018/9/11.
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

        logger.info("session:" + session.getId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
        User user  = (User)principalCollection.getPrimaryPrincipal();
        for(Role role:user.getRoles()){
            authorizationInfo.addRole(role.getRole_name());
            //这里关于用户的权限管理
        }
        for(Group p:user.getGroups()){
            authorizationInfo.addStringPermission(p.getGroup_name());
        }
        logger.info("本用户权限为:" + authorizationInfo.getRoles());
        return authorizationInfo;//把权限放入shiro中
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取前端输入的用户名
        String userName  = usernamePasswordToken.getUsername();
        System.out.println("NAME"+userName);
        //根据用户名查询数据库中对应的记录
        User user = userService.findByUserId(userName);
        System.out.println(user+"test yonghuming");
        //当前realm对象的name
        String realmName = getName();
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        //封装用户信息，构建AuthenticationInfo对象并返回
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(),credentialsSalt, realmName);
        return authcInfo;


    }
}