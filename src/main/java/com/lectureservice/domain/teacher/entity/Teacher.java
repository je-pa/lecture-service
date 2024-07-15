package com.lectureservice.domain.teacher.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.lectureservice.api.controller.teacher.dto.request.TeacherRequest;
import com.lectureservice.api.controller.teacher.dto.request.TeacherUpdateRequest;
import com.lectureservice.domain.BaseEntity;
import com.lectureservice.domain.teacher.type.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "teachers")
@NoArgsConstructor
public class Teacher extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "teacher_id")
  private Long id;

  @Column(nullable = false, length = 20)
  private String name;

  @Column(nullable = false)
  private Integer careerYears;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Company company;

  @Column(nullable = false, length = 14)
  private String phoneNumber;

  private String introduction;

  @Builder
  private Teacher(Integer careerYears, Company company, String introduction, String name,
      String phoneNumber) {
    this.careerYears = careerYears;
    this.company = company;
    this.introduction = introduction;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public static Teacher from(TeacherRequest request){
    return Teacher.builder()
        .careerYears(request.careerYears())
        .company(request.company())
        .introduction(request.introduction())
        .name(request.name())
        .phoneNumber(request.phoneNumber())
        .build();
  }

  public Teacher update(TeacherUpdateRequest request){
    this.careerYears = request.careerYears();
    this.company = request.company();
    this.introduction = request.introduction();
    this.phoneNumber = request.phoneNumber();
    return this;
  }
}
