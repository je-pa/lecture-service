package com.lectureservice.domain.lecture.repository.dto;

import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.domain.teacher.entity.Teacher;
import org.springframework.data.jpa.domain.Specification;

public class LectureSpecification {

  private static String TEACHER = "teacher";
  private static String CATEGORY = "category";

  public static Specification<Lecture> equalTeacher(final Teacher teacher) {
    return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get(TEACHER), teacher);
  }

  public static Specification<Lecture> equalCategory(final Category category) {
    return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get(CATEGORY), category);
  }

}
