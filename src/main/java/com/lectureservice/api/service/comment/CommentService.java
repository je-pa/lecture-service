package com.lectureservice.api.service.comment;

import com.lectureservice.api.controller.comment.dto.request.CommentRequest;
import com.lectureservice.domain.comment.Comment;
import com.lectureservice.domain.comment.repository.CommentRepository;
import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.repository.LectureRepository;
import com.lectureservice.domain.user.entity.User;
import com.lectureservice.domain.user.repository.UserRepository;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final LectureRepository lectureRepository;
  private final UserRepository userRepository;

  public void create(Long authorId, Long lectureId, CommentRequest request) {
    commentRepository.save(Comment.from(findUserById(authorId),
        findLectureById(lectureId), request));
  }

  public void reply(Long authorId, Long commentId, CommentRequest request) {
    commentRepository.save(Comment.reply(findUserById(authorId),
        findCommentById(commentId), request));
  }

  @Transactional
  public void update(Long userId, Long commentId, CommentRequest request) {
    Comment comment = findCommentById(commentId);
    validAuthorOrThrow(userId, comment);
    comment.update(request);
  }

  public void delete(Long userId, Long commentId) {
    Comment comment = findCommentById(commentId);
    validAuthorOrThrow(userId, comment);
    commentRepository.delete(comment);
  }

  private static void validAuthorOrThrow(Long userId, Comment comment) {
    if(!comment.isAuthorId(userId)){
      throw new CustomException(ExceptionCode.FORBIDDEN_COMMENT_UPDATE);
    }
  }

  private Comment findCommentById(Long commentId){
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
  }

  private Lecture findLectureById(Long lectureId){
    return lectureRepository.findById(lectureId)
        .orElseThrow(() -> new CustomException(ExceptionCode.LECTURE_NOT_FOUND));
  }

  private User findUserById(Long userId){
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));

  }
}
