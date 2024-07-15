package com.lectureservice.api.service.lecture;

import com.lectureservice.api.controller.lecture.dto.request.LectureRequest;
import com.lectureservice.api.controller.lecture.dto.request.LectureUpdateRequest;
import com.lectureservice.api.controller.lecture.dto.response.LectureDetailResponse;
import com.lectureservice.api.controller.lecture.dto.response.LecturesResponse;
import com.lectureservice.domain.comment.repository.CommentRepository;
import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.repository.LectureRepository;
import com.lectureservice.domain.lecture.repository.dto.LectureSpecificationBuilder;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.domain.teacher.entity.Teacher;
import com.lectureservice.domain.teacher.repository.TeacherRepository;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureService {
  private final static Pageable COMMENT_PAGEABLE
      = PageRequest.of(0,5, Sort.by(Order.desc("createdDateTime")));
  private final static int PAGE = 0;
  private final static int SIZE = 5;
  private final static int OFFSET = PAGE * SIZE;
  private final LectureRepository lectureRepository;
  private final TeacherRepository teacherRepository;
  private final CommentRepository commentRepository;

  public LectureDetailResponse create(LectureRequest request) {
    Teacher teacher = findTeacherById(request.teacherId());
    return LectureDetailResponse.from(lectureRepository.save(Lecture.from(request, teacher)));
  }

  @Transactional
  public LectureDetailResponse update(LectureUpdateRequest request, Long lectureId) {
    return LectureDetailResponse.from(findLectureById(lectureId).update(request));
  }

  @Transactional(readOnly = true)
  public LectureDetailResponse read(Long lectureId) {
    Lecture lecture = findLectureById(lectureId);
    return LectureDetailResponse.from(lecture,
        commentRepository.findParentCommentsWithChildCount(lecture, SIZE, OFFSET));
  }

  @Transactional(readOnly = true)
  public LecturesResponse readList(Long teacherId, Category category, Pageable pageable) {
    Teacher teacher = null;

    if(teacherId != null) {
      teacher = findTeacherById(teacherId);
    }

    return LecturesResponse.from(lectureRepository.findAll(
        LectureSpecificationBuilder.builder()
            .equalTeacher(teacher)
            .equalCategory(category)
            .build().lectureSpecification()
        , pageable));
  }

  public void delete(Long lectureId) {
    lectureRepository.deleteById(lectureId);
  }

  private Lecture findLectureById(Long lectureId){
    return lectureRepository.findById(lectureId)
        .orElseThrow(() -> new CustomException(ExceptionCode.LECTURE_NOT_FOUND));
  }

  private Teacher findTeacherById(Long teacherId) {
    return teacherRepository.findById(teacherId).orElseThrow(()->new CustomException(
        ExceptionCode.TEACHER_NOT_FOUND));
  }
}
