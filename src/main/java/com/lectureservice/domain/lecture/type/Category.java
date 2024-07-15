package com.lectureservice.domain.lecture.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
  SPRING("스프링"),
  REACT("리엑트"),
  NODE("노드");

  private final String value;
}
