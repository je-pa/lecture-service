package com.lectureservice.api.controller.lecture;

import com.lectureservice.api.service.lecture.LectureLikeService;
import com.lectureservice.global.security.anotation.CurrentUserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LectureLikeController {
  private final LectureLikeService lectureLikeService;

  @Operation(summary = "선택한 강의 좋아요 토글 기능", description = "인증이 된 사용자가 선택한 강의에 좋아요 버튼을 누른다.")
  @PostMapping("/lectures/{lectureId}/likes/toggle")
  public ResponseEntity<Void> toggleLike(@CurrentUserId Long currentUserId,
      @PathVariable Long lectureId){ // TODO: 플래그값
    return ResponseEntity.status(lectureLikeService.toggleLike(currentUserId, lectureId)).build();
  }

  @Operation(summary = "선택한 강의 좋아요 기능", description = "인증이 된 사용자가 선택한 강의에 좋아요를 한다.")
  @PostMapping("/lectures/{lectureId}/likes")
  public ResponseEntity<Void> create(@CurrentUserId Long currentUserId,
      @PathVariable Long lectureId){
    lectureLikeService.create(currentUserId, lectureId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "선택한 강의 좋아요 여부 조회 기능", description = "인증이 된 사용자가 선택한 강의에 좋아요 여부 확인한다.")
  @GetMapping("/lectures/{lectureId}/likes")
  public ResponseEntity<Boolean> isPresent(@CurrentUserId Long currentUserId,
      @PathVariable Long lectureId){
    return ResponseEntity.ok(lectureLikeService.isPresent(currentUserId, lectureId));
  }

  @Operation(summary = "선택한 강의 좋아요 취소 기능", description = "인증이 된 사용자가 선택한 강의에 좋아요를 취소한다.")
  @DeleteMapping("/lectures/{lectureId}/likes")
  public ResponseEntity<Void> delete(@CurrentUserId Long currentUserId,
      @PathVariable Long lectureId){
    lectureLikeService.delete(currentUserId, lectureId);
    return ResponseEntity.ok().build();
  }

}
