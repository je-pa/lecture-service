package com.lectureservice.api.controller.teacher;

import com.lectureservice.api.controller.teacher.dto.request.TeacherRequest;
import com.lectureservice.api.controller.teacher.dto.request.TeacherUpdateRequest;
import com.lectureservice.api.controller.teacher.dto.response.TeacherResponse;
import com.lectureservice.api.service.teacher.TeacherService;
import com.lectureservice.global.security.anotation.RoleAdmin;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RoleAdmin
@RequestMapping("/api/teachers")
public class TeacherController {
  private final TeacherService teacherService;

  @Operation(summary = "강사 등록", description = "관리자가 강사를 등록한다.")
  @PostMapping
  public ResponseEntity<TeacherResponse> create(@RequestBody @Valid TeacherRequest request){
    return ResponseEntity.ok(teacherService.create(request));
  }

  @Operation(summary = "강사 수정", description = "관리자가 강사를 수정한다.")
  @PutMapping("/{teacherId}")
  public ResponseEntity<TeacherResponse> update(@PathVariable Long teacherId,
      @RequestBody @Valid TeacherUpdateRequest request){
    return ResponseEntity.ok(teacherService.update(teacherId, request));
  }

  @Operation(summary = "선택한 강사 조회", description = "관리자가 선택한 강사를 조회한다.")
  @GetMapping("/{teacherId}")
  public ResponseEntity<TeacherResponse> read(@PathVariable Long teacherId){
    return ResponseEntity.ok(teacherService.read(teacherId));
  }

  @Operation(summary = "선택한 강사 삭제", description = "관리자가 선택한 강사를 삭제한다.")
  @DeleteMapping("{teacherId}")
  public ResponseEntity<Void> delete(@PathVariable Long teacherId){
    teacherService.delete(teacherId);
    return ResponseEntity.ok().build();
  }
}
