package com.swyp10.pinggyewang.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Target {
  BOSS_SENIOR("상사/선배"),
  TEACHER("교수/선생님"),
  COLLEAGUE_FRIEND("동료/친구"),
  LOVER_FAMILY("연인/가족"),
  OTHER("기타");

  private final String name;

  Target(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  @JsonCreator
  public static Target of(String name) {
    for (Target target : values()) {
      if (target.name.equals(name) ||
          target.name.contains(name)) {
        return target;
      }
    }
    return OTHER;
  }
}