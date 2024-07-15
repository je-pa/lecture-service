package com.lectureservice.domain.lecture.repository.dto;

import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.domain.teacher.entity.Teacher;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

@Builder
public class LectureSpecificationBuilder {
  private Teacher equalTeacher;
  private Category equalCategory;

  public Specification<Lecture> lectureSpecification() {
    Specification<Lecture> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    if (equalTeacher != null) {
      spec = spec.and(LectureSpecification.equalTeacher(equalTeacher));
    }

    if (equalCategory != null) {
      spec = spec.and(LectureSpecification.equalCategory(equalCategory));
    }

    return spec;
  }
}