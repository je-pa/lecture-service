package com.lectureservice.api.controller.comment.dto.response;

import com.lectureservice.domain.comment.Comment;
import com.lectureservice.domain.user.entity.User;
import lombok.Builder;

@Builder
public record CommentResponse(
    Long commentId,

    Long parentCommentId,

    AuthorResponse author,

    Long lectureId,

    String content,

    int replyCount
) {
  public static CommentResponse from(Comment comment){
    return CommentResponse.builder()
        .commentId(comment.getId())
        .parentCommentId(comment.getParentComment().getId())
        .author(AuthorResponse.from(comment.getAuthor()))
        .lectureId(comment.getLecture().getId())
        .content(comment.getContent())
        .build();
  }

  public record AuthorResponse(
      Long authorId,
      String email
  ) {

    public static AuthorResponse from(User user) {
      if(user == null) return null;
      return new AuthorResponse(
          user.getId(),
          user.getEmail());
    }
  }
}
