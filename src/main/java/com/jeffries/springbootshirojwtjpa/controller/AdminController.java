package com.jeffries.springbootshirojwtjpa.controller;

import com.jeffries.springbootshirojwtjpa.dao.UserRepository;
import com.jeffries.springbootshirojwtjpa.dto.ResultMap;
import com.jeffries.springbootshirojwtjpa.entity.User;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final ResultMap resultMap;
  private final UserRepository userRepository;

  public AdminController(ResultMap resultMap,
      UserRepository userRepository) {
    this.resultMap = resultMap;
    this.userRepository = userRepository;
  }

  @GetMapping(value = "/user/list")
  @RequiresRoles("admin")
  public ResultMap getUserList() {
    List<User> userList = userRepository.findAll();
    return resultMap.success().code(200).message(userList);
  }

  @PostMapping("/ban/user")
  @RequiresRoles("admin")
  public ResultMap banUser(String username) {
    User user = userRepository.findUserByUsername(username);
    user.setBan(1);
    userRepository.saveAndFlush(user);
    return resultMap.success().code(200).message("成功封号");
  }
}
