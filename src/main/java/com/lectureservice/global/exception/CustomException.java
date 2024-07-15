package com.lectureservice.global.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
  private final HttpStatus statusCode;

  public CustomException(ExceptionCode exceptionCode) {
    this(exceptionCode.getStatus(), exceptionCode.getMessage());
  }

  public CustomException(HttpStatus statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
