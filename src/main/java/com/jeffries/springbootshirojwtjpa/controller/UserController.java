package com.jeffries.springbootshirojwtjpa.controller;

import com.jeffries.springbootshirojwtjpa.dao.UserRepository;
import com.jeffries.springbootshirojwtjpa.dto.ResultMap;
import com.jeffries.springbootshirojwtjpa.entity.User;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository userRepository;
  private ResultMap resultMap;

  public UserController(UserRepository userRepository,
      ResultMap resultMap) {
    this.userRepository = userRepository;
    this.resultMap = resultMap;
  }

  @GetMapping(value = "/getMessage")
  @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
  public ResultMap getMessage() {
    return resultMap.success().code(200).message("成功获得信息！");
  }

  @PostMapping("/updatePassword")
  @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
  public ResultMap updatePassword(String username, String oldPassword, String newPassword) {
    User user = userRepository.findUserByUsername(username);
    String realPassword = user.getPassword();
    if(realPassword.equals(oldPassword)) {
      user.setPassword(newPassword);
      userRepository.saveAndFlush(user);
    }else{
      return resultMap.fail().message("密码错误！");
    }
    return resultMap.success().code(200).message("密码修改成功");
  }

  @GetMapping("/getVipMessage")
  @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
  @RequiresPermissions("vip")
  public ResultMap getVipMessage() {
    return resultMap.success().code(200).message("成功获得 vip 信息！");
  }
}
