package com.swyp10.pinggyewang.security.jwt;

import com.swyp10.pinggyewang.security.authentication.UserPrincipal;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserPrincipalClaimsConverter implements JwtClaimsConverter<UserPrincipal> {

  private static final String EMAIL_CLAIM = "email";
  private static final String ROLES_CLAIM = "roles";

  @Override
  public UserPrincipal convertToUserDetails(Claims claims) {
    final Long userId = extractUserId(claims);
    final String email = extractEmail(claims);
    final List<GrantedAuthority> authorities = extractAuthorities(claims);

    return new UserPrincipal(userId, email, authorities);
  }

  private Long extractUserId(Claims claims) {
    try {
      return Long.valueOf(claims.getSubject());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid user ID in JWT claims", e);
    }
  }

  private String extractEmail(Claims claims) {
    String email = claims.get(EMAIL_CLAIM, String.class);
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("Email is required in JWT claims");
    }
    return email;
  }

  private List<GrantedAuthority> extractAuthorities(Claims claims) {
    @SuppressWarnings("unchecked")
    List<String> roles = claims.get(ROLES_CLAIM, List.class);

    if (roles == null || roles.isEmpty()) {
      return List.of(new SimpleGrantedAuthority(getDefaultRole()));
    }

    return roles.stream()
        .map(this::normalizeRole)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private String normalizeRole(String role) {
    if (role == null || role.trim().isEmpty()) {
      return getDefaultRole();
    }

    String normalizedRole = role.trim().toUpperCase();
    return normalizedRole.startsWith("ROLE_") ? normalizedRole : "ROLE_" + normalizedRole;
  }
}
