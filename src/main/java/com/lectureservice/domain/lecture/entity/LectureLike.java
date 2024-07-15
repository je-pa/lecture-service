package com.lectureservice.domain.lecture.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.lectureservice.domain.BaseEntity;
import com.lectureservice.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "lecture_likes",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "lecture_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureLike extends BaseEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "lecture_like_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", nullable = false)
  private Lecture lecture;

  @Builder
  private LectureLike(Lecture lecture, User user) {
    this.lecture = lecture;
    this.user = user;
  }

  public static LectureLike from(User user, Lecture lecture){
    return LectureLike.builder()
        .lecture(lecture)
        .user(user)
        .build();
  }
}
