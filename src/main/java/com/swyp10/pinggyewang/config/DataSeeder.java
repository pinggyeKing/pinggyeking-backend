package com.swyp10.pinggyewang.config;

import com.swyp10.pinggyewang.domain.User;
import com.swyp10.pinggyewang.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

  @Bean
  CommandLineRunner init(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder
  ) {
    return args -> {
      if (userRepository.existsByEmail("test@gmail.com")) return;

      User testUser = new User("testuser", passwordEncoder.encode("1234"), "test@gmail.com");
      userRepository.save(testUser);
    };
  }
}
