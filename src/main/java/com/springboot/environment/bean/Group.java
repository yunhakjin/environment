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
@Table(name = "groups")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})

public class Group  implements Serializable {
    private static final long serialVersionUID = -509438491019594820L;

    @Id
    @GeneratedValue
    private int group_id;
    private String group_name;
    private String group_detail;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_group",joinColumns={@JoinColumn(name="group_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_detail() {
        return group_detail;
    }

    public void setGroup_detail(String group_detail) {
        this.group_detail = group_detail;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Group() {
    }

    @Override
    public String toString() {
        return "Group{" +
                "group_id=" + group_id +
                ", group_name='" + group_name + '\'' +
                ", group_detail='" + group_detail + '\'' +
                '}';
    }
}

