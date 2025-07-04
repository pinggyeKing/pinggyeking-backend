package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.domain.User;
import com.swyp10.pinggyewang.dto.request.LoginRequest;
import com.swyp10.pinggyewang.exception.ApplicationException;
import com.swyp10.pinggyewang.exception.AuthException;
import com.swyp10.pinggyewang.exception.CustomErrorCode;
import com.swyp10.pinggyewang.repository.UserRepository;
import com.swyp10.pinggyewang.security.jwt.JwtProvider;
import com.swyp10.pinggyewang.security.jwt.TokenIssuer;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final TokenIssuer tokenIssuer;
  private final PasswordEncoder passwordEncoder;

  public AuthService(final UserRepository userRepository, final TokenIssuer tokenIssuer,
      final PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.tokenIssuer = tokenIssuer;
    this.passwordEncoder = passwordEncoder;
  }

  public String login(final LoginRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new AuthException(CustomErrorCode.NOT_FOUNT_USER));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new AuthException(CustomErrorCode.PASSWORD_NOT_MATCHES);
    }

    return tokenIssuer.issue(user.getId(), user.getEmail(), List.of(user.getRole().name()));
  }
}
