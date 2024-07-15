package com.lectureservice.api.service.teacher;

import com.lectureservice.api.controller.teacher.dto.request.TeacherRequest;
import com.lectureservice.api.controller.teacher.dto.request.TeacherUpdateRequest;
import com.lectureservice.api.controller.teacher.dto.response.TeacherResponse;
import com.lectureservice.domain.teacher.entity.Teacher;
import com.lectureservice.domain.teacher.repository.TeacherRepository;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherResponse create(TeacherRequest teacher) {
    return TeacherResponse.from(teacherRepository.save(Teacher.from(teacher)));
  }

  @Transactional
  public TeacherResponse update(Long teacherId, TeacherUpdateRequest request) {
    return TeacherResponse.from(findTeacherById(teacherId)
        .update(request));
  }

  @Transactional(readOnly = true)
  public TeacherResponse read(Long teacherId) {
    return TeacherResponse.from(findTeacherById(teacherId));
  }

  public void delete(Long teacherId) {
    teacherRepository.deleteById(teacherId);
  }

  private Teacher findTeacherById(Long teacherId) {
    return teacherRepository.findById(teacherId)
        .orElseThrow(() -> new CustomException(ExceptionCode.TEACHER_NOT_FOUND));
  }
}
