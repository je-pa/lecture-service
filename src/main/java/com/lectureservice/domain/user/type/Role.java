package com.lectureservice.domain.user.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
  ADMIN("관리자", Authority.ADMIN),
  USER("일반유저", Authority.USER),;

  private final String value;
  @Getter
  private final String authority;

  public static class Authority {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
  }
}
