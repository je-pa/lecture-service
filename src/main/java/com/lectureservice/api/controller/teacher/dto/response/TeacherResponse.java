package com.lectureservice.api.controller.teacher.dto.response;

import com.lectureservice.domain.teacher.entity.Teacher;
import com.lectureservice.domain.teacher.type.Company;

public record TeacherResponse(
    Long teacherId,
    String name,
    Integer careerYears,
    Company company,
    String phoneNumber,
    String introduction
) {
  public static TeacherResponse from(Teacher teacher){
    if(teacher == null) return null;
    return new TeacherResponse(
      teacher.getId(),
      teacher.getName(),
      teacher.getCareerYears(),
      teacher.getCompany(),
      teacher.getPhoneNumber(),
      teacher.getIntroduction()
    );
  }
}
