package com.springboot.environment.service;

import com.springboot.environment.bean.Group;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
public interface GroupService {
    
    List<Group> getAll();

    void addOne(Group group);

    void updateOne(String group_id, String group_name);

    void delOne(String group_id);
}
