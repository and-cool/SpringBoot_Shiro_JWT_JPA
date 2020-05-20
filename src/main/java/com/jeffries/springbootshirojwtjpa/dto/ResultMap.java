package com.jeffries.springbootshirojwtjpa.dto;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class ResultMap extends HashMap<String, Object> {

  public ResultMap() {}

  public ResultMap success() {
    this.put("result", "success");
    return this;
  }

  public ResultMap fail() {
    this.put("result", "fail");
    return this;
  }

  public ResultMap code(int code) {
    this.put("code", code);
    return this;
  }

  public ResultMap message(Object message) {
    this.put("message", message);
    return this;
  }
}
