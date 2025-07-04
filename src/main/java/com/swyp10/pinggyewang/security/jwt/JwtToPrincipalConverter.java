package com.swyp10.pinggyewang.security.jwt;

import com.swyp10.pinggyewang.security.authentication.UserPrincipal;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtToPrincipalConverter {

  public UserPrincipal convert(final Claims claims) {
    final Long userId = Long.valueOf(claims.getSubject());
    final String email = claims.get("email", String.class);
    final List<GrantedAuthority> authorities = extractAuthorities(claims.get("roles", List.class));

    return new UserPrincipal(userId, email, authorities);
  }

  private List<GrantedAuthority> extractAuthorities(final List<String> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}