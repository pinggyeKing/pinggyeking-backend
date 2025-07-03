package com.swyp10.pinggyewang.dto.response;

public record LoginResponse(String accessToken) {

  public static LoginResponse of(final String accessToken) {
    return new LoginResponse(accessToken);
  }
}
