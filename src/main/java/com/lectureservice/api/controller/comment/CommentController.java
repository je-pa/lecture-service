package com.lectureservice.api.controller.comment;

import com.lectureservice.api.controller.comment.dto.request.CommentRequest;
import com.lectureservice.api.service.comment.CommentService;
import com.lectureservice.global.security.anotation.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
  private final CommentService commentService;

  @Operation(summary = "선택한 게시글에 댓글 등록", description = "인증이 된 사용자가 선택한 강의에 댓글을 작성한다.")
  @PostMapping("/lectures/{lectureId}/comments")
  public ResponseEntity<Void> create(@CurrentUserId Long userId,
      @PathVariable Long lectureId,
      @RequestBody @Valid CommentRequest request) {
    commentService.create(userId, lectureId, request);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "선택한 댓글의 루트 댓글에 대댓글 등록", description = "인증이 된 사용자가 선택한 댓글의 루트 댓글에 댓글을 작성한다.")
  @PostMapping("/comments/{commentId}/comments")
  public ResponseEntity<Void> reply(@CurrentUserId Long userId,
      @PathVariable Long commentId,
      @RequestBody @Valid CommentRequest request
      ) {
    commentService.reply(userId, commentId, request);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "선택한 댓글 수정", description = "댓글 작성자가 선택한 댓글을 수정한다.")
  @PatchMapping("/comments/{commentId}")
  public ResponseEntity<Void> update(@CurrentUserId Long userId,
      @PathVariable Long commentId,
      @RequestBody @Valid CommentRequest request
  ) {
    commentService.update(userId, commentId, request);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "선택한 댓글 삭제", description = "댓글 작성자가 선택한 댓글을 삭제한다.")
  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> delete(@CurrentUserId Long userId,
      @PathVariable Long commentId
  ) {
    commentService.delete(userId, commentId);
    return ResponseEntity.ok().build();
  }
}
