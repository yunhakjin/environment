package com.springboot.environment.dao;

import com.springboot.environment.bean.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yww on 2018/9/11.
 */
public interface GroupDao extends JpaRepository<Group,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update `groups` set group_name=?2,group_detail=?3  where group_id=?1 ",nativeQuery = true)
    void updateOne(int group_id, String groupName, String group_detail);

    @Transactional
    @Modifying
    @Query(value = "delete from  user_group where group_id = ?1",nativeQuery = true)
    void deleteGroupUser(int group_id);

    @Query(value = "select DISTINCT * from groups",nativeQuery = true)
    List< Object[]> getAll();
}
