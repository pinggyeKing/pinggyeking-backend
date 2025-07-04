package com.swyp10.pinggyewang.security.jwt;

import io.jsonwebtoken.Claims;

public interface TokenParser {

  Claims parse(final String token);
}
