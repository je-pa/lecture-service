package com.lectureservice.domain.comment.repository.dto;

import com.lectureservice.domain.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWithChildCountDto {

  private Comment comment;
  private Long childCommentCount;

  public CommentWithChildCountDto(Comment comment, Long childCommentCount) {
    this.comment = comment;
    this.childCommentCount = childCommentCount;
  }

}