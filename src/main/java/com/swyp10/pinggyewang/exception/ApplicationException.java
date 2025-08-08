package com.swyp10.pinggyewang.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

  private CustomErrorCode errorCode;

  public ApplicationException(final CustomErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ApplicationException(String message) {
    super(message);
  }
}
