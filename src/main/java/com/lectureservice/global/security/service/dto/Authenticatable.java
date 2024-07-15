package com.lectureservice.global.security.service.dto;

import com.lectureservice.domain.user.type.Role;

public interface Authenticatable {
  Long getId();
  String getPassword();
  Role getRole();
}
