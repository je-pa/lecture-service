package com.lectureservice.domain.comment.repository;

import com.lectureservice.domain.comment.Comment;
import com.lectureservice.domain.comment.repository.dto.CommentWithChildCountDto;
import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE Comment c SET c.deletedDateTime = NOW(), "
      + "c.content = '탈퇴한 회원의 댓글입니다.', "
      + "c.author = null "
      + "WHERE c.author = :user")
  void deleteAllInBatchByAuthor(User user);

  @EntityGraph(attributePaths = "author")
  @Query("SELECT new com.lectureservice.domain.comment.repository.dto"+
      ".CommentWithChildCountDto(c, COUNT(child)) " +
      "FROM Comment c LEFT JOIN Comment child ON c.id = child.parentComment.id AND c.lecture = :lecture " +
      "WHERE c.parentComment IS NULL " +
      "GROUP BY c.id ")
  Page<CommentWithChildCountDto> findParentCommentsWithChildCount(Lecture lecture, Pageable pageable);

  @EntityGraph(attributePaths = "author")
  @Query("SELECT new com.lectureservice.domain.comment.repository.dto"+
      ".CommentWithChildCountDto(c, COUNT(child)) " +
      "FROM Comment c LEFT JOIN Comment child ON c.id = child.parentComment.id AND c.lecture = :lecture " +
      "WHERE c.parentComment IS NULL " +
      "GROUP BY c.id "+
      "ORDER BY c.createdDateTime desc "+
      "LIMIT :size OFFSET :offset")
  List<CommentWithChildCountDto> findParentCommentsWithChildCount(Lecture lecture, int size, int offset);
}
