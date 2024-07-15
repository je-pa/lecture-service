package com.lectureservice.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
  // BAD_REQUEST: 400
  NEGATIVE_LIKE_COUNT(BAD_REQUEST, "좋아요 수는 음수가 될 수 없습니다."),
  CANNOT_DECREASE_LIKE_COUNT(BAD_REQUEST, "좋아요 수를 차감할 수 없는 상태입니다."),
  INVALID_JWT_SIGNATURE(BAD_REQUEST, "유효하지 않는 JWT 서명 입니다."),
  UNSUPPORTED_JWT_TOKEN(BAD_REQUEST, "지원되지 않는 JWT 토큰 입니다."),
  JWT_CLAIMS_EMPTY(BAD_REQUEST, "잘못된 JWT 토큰 입니다."),

  // Unauthorized: 401
  TOKEN_EXPIRED(UNAUTHORIZED, "만료된 token 입니다."),
  PASSWORD_MISMATCH(UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

  // FORBIDDEN: 403
  FORBIDDEN_COMMENT_UPDATE(FORBIDDEN, "댓글을 수정할 권한이 없습니다.(댓글은 작성자만 수정이 가능합니다.)"),

  // NOT_FOUND: 404
  TEACHER_NOT_FOUND(NOT_FOUND, "강사 개체를 찾지 못했습니다."),
  LECTURE_NOT_FOUND(NOT_FOUND, "강의 개체를 찾지 못했습니다."),
  LECTURE_LIKE_NOT_FOUND(NOT_FOUND, "강의 좋아요 개체를 찾지 못했습니다."),
  USER_NOT_FOUND(NOT_FOUND, "유저 개체를 찾지 못했습니다."),
  COMMENT_NOT_FOUND(NOT_FOUND, "댓글 개체를 찾지 못했습니다."),

  // Conflict: 409,
  EMAIL_CONFLICT(CONFLICT, "이메일이 중복됩니다.");

  private final HttpStatus status;
  private final String message;
}
