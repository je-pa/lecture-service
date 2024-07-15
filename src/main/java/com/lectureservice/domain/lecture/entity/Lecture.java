package com.lectureservice.domain.lecture.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.lectureservice.api.controller.lecture.dto.request.LectureRequest;
import com.lectureservice.api.controller.lecture.dto.request.LectureUpdateRequest;
import com.lectureservice.domain.BaseEntity;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.domain.teacher.entity.Teacher;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lectures")
@NoArgsConstructor(access = PROTECTED)
public class Lecture extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "lecture_id")
  private Long id;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private String introduction;

  @Enumerated(STRING)
  @Column(nullable = false)
  private Category category;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  private Integer likeCount;

  @Builder
  private Lecture(Category category, String introduction, String title, Integer price,
      Teacher teacher) {
    this.category = category;
    this.introduction = introduction;
    this.title = title;
    this.price = price;
    this.teacher = teacher;
  }

  public static Lecture from(LectureRequest request, Teacher teacher) {
    return Lecture.builder()
        .title(request.title())
        .price(request.price())
        .introduction(request.introduction())
        .category(request.category())
        .teacher(teacher)
        .build();
  }

  public Lecture update(LectureUpdateRequest request) {
    this.title = request.title();
    this.price = request.price();
    this.introduction = request.introduction();
    this.category = request.category();
    return this;
  }

  public void increaseLikeCount(){
    if(likeCount==null){
      likeCount = 1;
      return;
    }
    this.likeCount++;
  }

  public void decreaseLikeCount(){
    if (this.likeCount == null || this.likeCount <= 0) {
      throw new CustomException(ExceptionCode.NEGATIVE_LIKE_COUNT);
    }
    this.likeCount--;
  }
}
