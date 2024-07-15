package com.lectureservice.api.controller.lecture.dto.response;

import com.lectureservice.domain.comment.Comment;
import com.lectureservice.domain.comment.repository.dto.CommentWithChildCountDto;
import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.type.Category;
import com.lectureservice.domain.teacher.entity.Teacher;
import com.lectureservice.domain.teacher.type.Company;
import java.time.LocalDateTime;
import java.util.List;

public record LectureDetailResponse(
    Long id,

    String title,

    Integer price,

    String introduction,

    Category category,

    TeacherResponse teacher,

    Integer likeCount,

    List<CommentResponse> comments
) {
  public static LectureDetailResponse from(Lecture lecture) {
    if(lecture == null) return null;
    return from(lecture, null);
  }
  public static LectureDetailResponse from(Lecture lecture, List<CommentWithChildCountDto> comments) {
    if(lecture == null) return null;
    return new LectureDetailResponse(
        lecture.getId(),
        lecture.getTitle(),
        lecture.getPrice(),
        lecture.getIntroduction(),
        lecture.getCategory(),
        TeacherResponse.from(lecture.getTeacher()),
        lecture.getLikeCount(),
        comments == null ? null : comments.stream().map(CommentResponse::from).toList()
    );
  }

  public record TeacherResponse(
      Long teacherId,
      String name,
      Integer careerYears,
      Company company,
      String introduction
  ) {
    private static TeacherResponse from(Teacher teacher){
      if(teacher == null) return null;
      return new TeacherResponse(
          teacher.getId(),
          teacher.getName(),
          teacher.getCareerYears(),
          teacher.getCompany(),
          teacher.getIntroduction()
      );
    }
  }

  public record CommentResponse(
    Long commentId,
    Long authorId,
    String authorEmail,
    String content,
    Long replyCount,
    LocalDateTime createdDateTime
  ){
    private static CommentResponse from(CommentWithChildCountDto dto){
      if(dto == null) return null;
      Comment comment = dto.getComment();
      return new CommentResponse(
        comment.getId(),
          comment.getAuthor().getId(),
          comment.getAuthor().getEmail(),
          comment.getContent(),
          dto.getChildCommentCount(),
          comment.getCreatedDateTime()
      );
    }
  }
}
