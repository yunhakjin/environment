package com.springboot.environment.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yww on 2018/10/13.
 */
@Data
@Entity
@Table(name = "permission")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Permission implements Serializable {
    private static final long serialVersionUID = -509438491019594820L;
    @Id
    @GeneratedValue
    private int permission_id;

    @Column(name = "permission_name")
    private String permission_name;

    //*/ 用户 - 角色关系定义;多对多
    @ManyToMany(mappedBy = "permissions" ,fetch = FetchType.LAZY)
//    @JoinTable(name="e_user_role",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<Role> roles;// 一个角色对应多个用户

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permission_id=" + permission_id +
                ", permission_name='" + permission_name +
                '}';
    }

    public Permission() {
    }
}
