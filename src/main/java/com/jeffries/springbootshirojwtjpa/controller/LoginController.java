package com.jeffries.springbootshirojwtjpa.controller;

import com.jeffries.springbootshirojwtjpa.dao.UserRepository;
import com.jeffries.springbootshirojwtjpa.dto.ResultMap;
import com.jeffries.springbootshirojwtjpa.entity.User;
import com.jeffries.springbootshirojwtjpa.entity.UserRole;
import com.jeffries.springbootshirojwtjpa.util.JWTUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final ResultMap resultMap;

  private final UserRepository userRepository;

  public LoginController(ResultMap resultMap,
      UserRepository userRepository) {
    this.resultMap = resultMap;
    this.userRepository = userRepository;
  }

  @GetMapping(value = "/logout")
  public ResultMap logout() {
    Subject subject = SecurityUtils.getSubject();
    //注销
    subject.logout();
    return resultMap.success().message("成功注销！");
  }

  @PostMapping(value = "/login")
  public ResultMap login(@RequestParam("username") String username, @RequestParam("password") String password) {

    String realPassword = userRepository.findUserByUsername(username).getPassword();

    if(password == null) {
      return resultMap.fail().code(401).message("用户名错误");
    }else if(!realPassword.equals(password)){
      return resultMap.fail().code(401).message("密码错误");
    }else{
      return resultMap.success().code(200).message(JWTUtil.createToken(username));
    }
  }

  @RequestMapping(path = "/unauthorized/{message}")
  public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
    return resultMap.success().code(401).message(message);
  }

}
