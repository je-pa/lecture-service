package com.lectureservice.api.controller.user.dto.response;

public record SigninResponse(
    String token
) {

  public static SigninResponse from(String token) {
    return new SigninResponse(
        token
    );
  }
}
