package com.lectureservice.api.controller.teacher.dto.request;


import com.lectureservice.domain.teacher.type.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeacherRequest(
    @NotBlank
    String name,
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
