package com.swyp10.pinggyewang.exception;

public enum CustomErrorCode {

  INTERNAL_SERVER_ERROR(500, "COMMON_001", "내부 서버 오류가 발생했습니다."),
  PASSWORD_NOT_MATCHES(404, "PW_001", "비밀번호가 일치하지 않습니다."),
  NOT_FOUNT_USER(404, "USER_001", "해당 이메일의 유저를 찾을 수 없습니다."),

  // 피드백 관련 예외
  RATING_REQUIRED(400, "FEEDBACK_001", "평점은 필수입니다."),
  INVALID_RATING_VALUE(400, "FEEDBACK_004", "평점 값이 올바르지 않습니다."),
  FEEDBACK_CONTENT_REQUIRED(400, "FEEDBACK_002", "피드백 내용은 필수입니다."),
  FEEDBACK_CONTENT_TOO_LONG(400, "FEEDBACK_003", "피드백은 1000자 이하로 작성해주세요.");

  private final int status;
  private final String code;
  private final String message;

  CustomErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}