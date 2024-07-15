package com.lectureservice.domain.user.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
  MALE("남성"),
  FEMALE("여성"),
  NONE("없음");

  private final String value;
}
