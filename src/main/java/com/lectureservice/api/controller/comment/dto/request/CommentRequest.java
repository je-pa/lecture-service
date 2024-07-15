package com.lectureservice.api.controller.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
    @NotBlank
    String content
) {

}
