package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
@Data
@Entity
@Table(name = "role")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})

public class Role  implements Serializable {
    private static final long serialVersionUID = -509438491019594820L;

    @Id
    @GeneratedValue
    private int role_id;
    private String role_name;
    private String permission_list;
    private String menu_list;
    private String describes;
    public String getPermission_list() {
        return permission_list;
    }

    public void setPermission_list(String permission_list) {
        this.permission_list = permission_list;
    }

    public String getMenu_list() {
        return menu_list;
    }

    public void setMenu_list(String menu_list) {
        this.menu_list = menu_list;
    }

    public String getDescribe() {
        return describes;
    }

    public void setDescribe(String describe) {
        this.describes = describe;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", permission_list='" + permission_list + '\'' +
                ", menu_list='" + menu_list + '\'' +
                ", describe='" + describes + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }

    // 用户 - 角色关系定义;多对多
    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.LAZY)
//    @JoinTable(name="e_user_role",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users;// 一个角色对应多个用户


    //指定了多对多的关系，fetch=FetchType.LAZY属性表示在多的那一方通过延迟加载的方式加载对象（默认不是延迟加载）
    @ManyToMany(fetch= FetchType.LAZY)//立即从数据库中进行加载数据;
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns ={@JoinColumn(name = "permission_id") })
    private List<Permission> permissions;// 一个用户具有多个角色

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Role() {
    }

}
