package com.lectureservice.api.controller.user.dto.request;

import com.lectureservice.api.controller.annotation.ValidPassword;
import com.lectureservice.api.controller.annotation.ValidPhoneNumber;
import com.lectureservice.domain.user.type.Gender;
import com.lectureservice.domain.user.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserRequest(
    @NotBlank
    @Email
    String email,
    @NotBlank
    @ValidPassword
    String password,
    @NotNull
    Gender gender,
    @ValidPhoneNumber
    String phoneNumber,
    @NotBlank
    String address,
    @NotNull
    Role role
) {

}
