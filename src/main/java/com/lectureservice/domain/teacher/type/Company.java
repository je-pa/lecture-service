package com.lectureservice.domain.teacher.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Company {
  TEAM_SPARTA("팀스파르타(주)"),
  NAVER("네이버(주)"),
  KAKAO("카카오(주)");

  private final String value;
}
