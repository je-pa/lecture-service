package com.lectureservice.api.controller.lecture.dto.request;

import com.lectureservice.domain.lecture.type.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LectureRequest(

    @NotBlank
    String title,

    @NotNull
    @Positive
    Integer price,

    @NotBlank
    String introduction,

    @NotNull
    Category category,

    @NotNull
    Long teacherId
) {

}
