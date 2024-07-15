package com.lectureservice.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.lectureservice.api.controller.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
    HttpStatus status = INTERNAL_SERVER_ERROR;
    log(e, request, status);
    return ResponseEntity.status(status).body(ApiResponse.of(status, e.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse<Void>> handleException(RuntimeException e, HttpServletRequest request) {
    HttpStatus status = BAD_REQUEST;
    log(e, request, status);
    return ResponseEntity.status(status).body(ApiResponse.of(status, e.getMessage()));
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleException(CustomException e, HttpServletRequest request) {
    log(e, request, e.getStatusCode());
    return ResponseEntity.status(e.getStatusCode()).body(ApiResponse.of(e.getStatusCode(), e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
    HttpStatus status = BAD_REQUEST;
    log(e, request, status);
    return ResponseEntity.status(status).body(ApiResponse.of(status, e.getMessage()));
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiResponse<Void>> handleException(
      AuthorizationDeniedException e, HttpServletRequest request) {
    HttpStatus status = FORBIDDEN;
    log(e, request, status);
    return ResponseEntity.status(status).body(ApiResponse.of(status, e.getMessage()));
  }

  private static void log(Exception e, HttpServletRequest request, HttpStatus status) {
    log.error("{}:{}:{}:{}"
        , request.getRequestURI(), status.value(), e.getClass().getSimpleName(), e.getMessage());
  }
}
