package com.springboot.environment.dao;

import com.springboot.environment.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface PersonDao extends JpaRepository<Person,Integer> {
}
