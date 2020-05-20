package com.jeffries.springbootshirojwtjpa.dao;

import com.jeffries.springbootshirojwtjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findUserByUsername(String username);

}
