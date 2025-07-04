package com.swyp10.pinggyewang.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtClaimsConverter<T extends UserDetails> {

  T convertToUserDetails(Claims claims);

  default String getDefaultRole() {
    return "ROLE_USER";
  }
}
