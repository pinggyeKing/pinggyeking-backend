package com.swyp10.pinggyewang.controller;

import com.swyp10.pinggyewang.security.authentication.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/")
  public String greeting() {
    return "hello!";
  }

  @GetMapping("/secured")
  public String secured(@AuthenticationPrincipal UserPrincipal principal) {
    return "secure test - user login: user email: " + principal.getUsername() + " user id: " + principal.getId();
  }
}
