package com.swyp10.pinggyewang.exception;

public class AuthException extends ApplicationException {

  public AuthException(final CustomErrorCode errorCode) {
    super(errorCode);
  }
}
