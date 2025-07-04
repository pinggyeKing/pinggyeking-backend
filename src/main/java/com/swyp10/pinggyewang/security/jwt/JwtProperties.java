package com.swyp10.pinggyewang.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record JwtProperties(
    @Value("${jwt.secret}") String secretKey,
    @Value("${jwt.expiration:86400000}") Long expiration
) {
}