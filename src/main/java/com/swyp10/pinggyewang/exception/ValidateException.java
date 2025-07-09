package com.swyp10.pinggyewang.exception;

public class ValidateException extends ApplicationException {

  public ValidateException(final CustomErrorCode errorCode) {
    super(errorCode);
  }
}
