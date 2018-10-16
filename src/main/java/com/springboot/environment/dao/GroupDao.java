package com.springboot.environment.dao;

import com.springboot.environment.bean.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yww on 2018/9/11.
 */
public interface GroupDao extends JpaRepository<Group,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update group set group_name=?2 where group_id=?1 ",nativeQuery = true)
    void updateOne(int group_id, String group_name);

    @Transactional
    @Modifying
    @Query(value = "delete from  user_group where group_id = ?1",nativeQuery = true)
    void deleteGroupUser(int group_id);
}
