package com.swyp10.pinggyewang.security.filter;

import com.swyp10.pinggyewang.security.jwt.JwtToPrincipalConverter;
import com.swyp10.pinggyewang.security.authentication.UserPrincipalAuthenticationToken;
import com.swyp10.pinggyewang.security.jwt.TokenParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  public static final int BEGIN_INDEX = 7;

  private final TokenParser tokenParser;
  private final JwtToPrincipalConverter jwtToPrincipalConverter;

  public JwtAuthenticationFilter(final TokenParser tokenParser,
      final JwtToPrincipalConverter jwtToPrincipalConverter) {
    this.tokenParser = tokenParser;
    this.jwtToPrincipalConverter = jwtToPrincipalConverter;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
      final HttpServletResponse response, final FilterChain filterChain)
      throws ServletException, IOException {

    extractTokenFromRequest(request)
        .map(tokenParser::parse)
        .map(jwtToPrincipalConverter::convert)
        .map(UserPrincipalAuthenticationToken::new)
        .ifPresent(authentication -> {
          SecurityContextHolder.getContext().setAuthentication(authentication);
        });

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
    final String token = request.getHeader("Authorization");

    if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
      return Optional.of(token.substring(BEGIN_INDEX));
    }

    return Optional.empty();
  }
}
