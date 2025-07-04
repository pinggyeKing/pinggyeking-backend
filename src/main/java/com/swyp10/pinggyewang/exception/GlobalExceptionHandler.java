package com.swyp10.pinggyewang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<CustomErrorResponse> handleAuthException(AuthException ex) {
    final CustomErrorResponse response = CustomErrorResponse.of(ex.getErrorCode());

    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(response);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<CustomErrorResponse> handleRuntimeException(ApplicationException ex) {
    final CustomErrorResponse response = CustomErrorResponse.of(ex.getErrorCode());

    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<CustomErrorResponse> handleRuntimeException(RuntimeException ex) {
    final CustomErrorResponse response = CustomErrorResponse.of(ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
