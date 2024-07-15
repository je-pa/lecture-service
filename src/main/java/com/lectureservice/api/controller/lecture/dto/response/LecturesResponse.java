package com.lectureservice.api.controller.lecture.dto.response;

import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.type.Category;
import org.springframework.data.domain.Page;

public record LecturesResponse(

    Page<LectureResponse> lectures

) {

  public static LecturesResponse from(Page<Lecture> lectures) {
    if(lectures == null) return null;
    return new LecturesResponse(lectures.map(LectureResponse::from));
  }

  public record LectureResponse(

      Long id,

      String title,

      Integer price,

      String introduction,

      Category category,

      Integer likeCount

  ) {

    public static LectureResponse from(Lecture lecture) {
      if(lecture == null) return null;
      return new LectureResponse(
          lecture.getId(),
          lecture.getTitle(),
          lecture.getPrice(),
          lecture.getIntroduction(),
          lecture.getCategory(),
          lecture.getLikeCount()
      );
    }

  }

}
