package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.dto.request.LoginRequest;
import com.swyp10.pinggyewang.dto.response.LoginResponse;
import com.swyp10.pinggyewang.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthController {

  private final AuthService authService;

  public RestAuthController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/auth/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    final String token = authService.login(request);

    return LoginResponse.of(token);
  }
}
