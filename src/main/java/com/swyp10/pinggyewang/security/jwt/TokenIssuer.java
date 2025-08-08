package com.swyp10.pinggyewang.security.jwt;

import java.util.List;

public interface TokenIssuer {

  String issue(Long userId, String email, List<String> roles);
}
