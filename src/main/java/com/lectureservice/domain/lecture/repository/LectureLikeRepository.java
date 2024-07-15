package com.lectureservice.domain.lecture.repository;

import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.entity.LectureLike;
import com.lectureservice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureLikeRepository extends JpaRepository<LectureLike, Long> {

  Optional<LectureLike> findByUserAndLecture(User user, Lecture lecture);
}
