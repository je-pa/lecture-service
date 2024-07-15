package com.lectureservice.api.controller.lecture;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.lectureservice.api.controller.lecture.dto.request.LectureRequest;
import com.lectureservice.api.controller.lecture.dto.request.LectureUpdateRequest;
import com.lectureservice.api.controller.lecture.dto.response.LectureDetailResponse;
import com.lectureservice.api.controller.lecture.dto.response.LecturesResponse;
import com.lectureservice.api.service.lecture.LectureService;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.global.security.anotation.RoleAdmin;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureController {

  private final LectureService lectureService;

  @Operation(summary = "강의 등록", description = "관리자가 강의를 등록한다.")
  @RoleAdmin
  @PostMapping
  public ResponseEntity<LectureDetailResponse> create(@Valid @RequestBody LectureRequest request){
    return ResponseEntity.ok(lectureService.create(request));
  }

  @Operation(summary = "강의 수정", description = "관리자가 강의를 수정한다.")
  @RoleAdmin
  @PutMapping("{lectureId}")
  public ResponseEntity<LectureDetailResponse> update(
      @Valid @RequestBody LectureUpdateRequest request,
      @PathVariable Long lectureId){
    return ResponseEntity.ok(lectureService.update(request, lectureId));
  }

  @Operation(summary = "선택한 강의 조회", description = "선택한 강의를 조회한다.")
  @GetMapping("/{lectureId}")
  public ResponseEntity<LectureDetailResponse> read(@PathVariable Long lectureId){
    return ResponseEntity.ok(lectureService.read(lectureId));
  }

  @Operation(summary = "강의 목록 조회", description = "강의 목록을 조회한다.")
  @GetMapping
  public ResponseEntity<LecturesResponse> readListByTeacherId(
      @RequestParam(value = "teacher_id", required = false) Long teacherId,
      @RequestParam(value = "category", required = false) Category category,
      @PageableDefault(sort = "createdDateTime", direction = DESC) Pageable pageable){
    return ResponseEntity.ok(lectureService.readList(teacherId, category, pageable));
  }

  @Operation(summary = "강의 삭제", description = "관리자가 선택한 강의를 삭제한다.")
  @RoleAdmin
  @DeleteMapping("{lectureId}")
  public ResponseEntity<Void> delete(
      @PathVariable Long lectureId){
    lectureService.delete(lectureId);
    return ResponseEntity.ok().build();
  }
}
