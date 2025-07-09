package com.swyp10.pinggyewang.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swyp10.pinggyewang.exception.ApplicationException;
import com.swyp10.pinggyewang.exception.CustomErrorCode;

public enum Rating {
  LIKE, DISLIKE;

  @JsonCreator
  public static Rating from(String value) {
    try {
      return Rating.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new ApplicationException(CustomErrorCode.RATING_REQUIRED);
    }
  }
}
