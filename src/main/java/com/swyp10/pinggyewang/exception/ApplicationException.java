package com.swyp10.pinggyewang.exception;

public class ApplicationException extends RuntimeException {

  private final CustomErrorCode errorCode;

  public ApplicationException(final CustomErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public CustomErrorCode getErrorCode() {
    return errorCode;
  }
}
