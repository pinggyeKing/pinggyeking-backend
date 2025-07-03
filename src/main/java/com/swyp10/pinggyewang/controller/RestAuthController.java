package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.request.LoginRequest;
import com.swyp10.pinggyewang.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthController {

  @PostMapping("/auth/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    return LoginResponse.of("access-token-test");
  }
}
