package com.lectureservice.api.controller.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password
) {

}
