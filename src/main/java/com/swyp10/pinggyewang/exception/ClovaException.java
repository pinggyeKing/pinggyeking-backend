package com.swyp10.pinggyewang.exception;

public class ClovaException extends ApplicationException {

  public ClovaException(final CustomErrorCode errorCode) {
    super(errorCode);
  }
}
