package com.lectureservice.domain.comment;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.lectureservice.api.controller.comment.dto.request.CommentRequest;
import com.lectureservice.domain.BaseEntity;
import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comments SET deleted_date_time = NOW(), content = '삭제된 댓글입니다.' WHERE comment_id = ?")
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  private Comment parentComment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", nullable = false)
  private Lecture lecture;

  @Column(nullable = false)
  private String content;

  private LocalDateTime deletedDateTime;

  @Builder
  private Comment(User author, Comment parentComment, String content, Lecture lecture) {
    this.author = author;
    this.parentComment = parentComment;
    this.content = content;
    this.lecture = lecture;
  }

  public static Comment from(User author, Lecture lecture, CommentRequest request) {
    return Comment.builder()
        .author(author)
        .lecture(lecture)
        .content(request.content())
        .build();
  }

  public static Comment reply(User author, Comment comment, CommentRequest request) {
    return Comment.builder()
        .parentComment(getRootComment(comment))
        .author(author)
        .lecture(comment.lecture)
        .content(request.content())
        .build();
  }

  public boolean isAuthorId(Long authorId){
    if(authorId == null) return false;
    return authorId.equals(this.author.getId());
  }

  public void update(CommentRequest request) {
    this.content = request.content();
  }

  private static Comment getRootComment(Comment comment) {
    Comment rootComment = comment;
    while (rootComment.getParentComment() != null) {
      rootComment = rootComment.getParentComment();
    }
    return rootComment;
  }
}
