package com.lectureservice.api.controller.teacher.dto.request;


import com.lectureservice.domain.teacher.type.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeacherUpdateRequest(
    @NotNull
    Integer careerYears,
    @NotNull
    Company company,
    @NotBlank
    String phoneNumber,
    @NotBlank
    String introduction
) {

}
