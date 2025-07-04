package com.swyp10.pinggyewang.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final JwtProperties jwtProperties;

  public JwtProvider(final JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8));
  }

  public String issue(Long userId, String email, List<String> roles) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", email);
    claims.put("roles", roles);

    Instant now = Instant.now();
    Instant expiryInstant = now.plusMillis(jwtProperties.expiration());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(String.valueOf(userId))
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiryInstant))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }
}
