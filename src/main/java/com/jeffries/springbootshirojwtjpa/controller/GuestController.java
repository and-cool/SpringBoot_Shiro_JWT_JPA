package com.jeffries.springbootshirojwtjpa.controller;

import com.jeffries.springbootshirojwtjpa.dto.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {

  private final ResultMap resultMap;

  public GuestController(ResultMap resultMap) {
    this.resultMap = resultMap;
  }

  @GetMapping(value = "/welcome")
  public ResultMap login() {
    return resultMap.success().code(200).message("欢迎访问游客页面");
  }


}
