package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.domain.User;
import com.swyp10.pinggyewang.dto.request.LoginRequest;
import com.swyp10.pinggyewang.repository.UserRepository;
import com.swyp10.pinggyewang.security.jwt.JwtProvider;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final PasswordEncoder passwordEncoder;

  public AuthService(final UserRepository userRepository, final JwtProvider jwtProvider,
      final PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtProvider = jwtProvider;
    this.passwordEncoder = passwordEncoder;
  }

  public String login(final LoginRequest request) {
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return jwtProvider.issue(user.getId(), user.getEmail(), List.of(user.getRole().name()));
  }
}
