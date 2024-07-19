package com.lectureservice.api.service.lecture;

import com.lectureservice.domain.lecture.entity.Lecture;
import com.lectureservice.domain.lecture.entity.LectureLike;
import com.lectureservice.domain.lecture.repository.LectureLikeRepository;
import com.lectureservice.domain.lecture.repository.LectureRepository;
import com.lectureservice.domain.user.entity.User;
import com.lectureservice.domain.user.repository.UserRepository;
import com.lectureservice.global.exception.CustomException;
import com.lectureservice.global.exception.ExceptionCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LectureLikeService {

  private final LectureLikeRepository lectureLikeRepository;
  private final LectureRepository lectureRepository;
  private final UserRepository userRepository;

  @Transactional
  public HttpStatus toggleLike(Long userId, Long lectureId) {
    Lecture lecture = findLectureById(lectureId);
    User user = findUserById(userId);

    Optional<LectureLike> existingLike = lectureLikeRepository.findByUserAndLecture(user, lecture);

    if (existingLike.isPresent()) {
      removeLike(existingLike.get(), lecture);
      return HttpStatus.NO_CONTENT;
    }

    addLike(user, lecture);
    return HttpStatus.CREATED;
  }

  public void create(Long userId, Long lectureId) {
    addLike(findUserById(userId), findLectureById(lectureId));
  }

  public Boolean isPresent(Long userId, Long lectureId) {
    return lectureLikeRepository.findByUserAndLecture(
        findUserById(userId), findLectureById(lectureId)).isPresent();
  }

  public void delete(Long userId, Long lectureId) {
    Lecture lecture = findLectureById(lectureId);
    LectureLike lectureLike = lectureLikeRepository.findByUserAndLecture(
        findUserById(userId), lecture)
        .orElseThrow(() -> new CustomException(ExceptionCode.LECTURE_LIKE_NOT_FOUND));
    removeLike(lectureLike, lecture);
  }

  private void addLike(User user, Lecture lecture) {
    lecture.increaseLikeCount();
    lectureLikeRepository.save(LectureLike.from(user, lecture));
  }

  private void removeLike(LectureLike lectureLike, Lecture lecture) {
    if (lecture.getLikeCount() == null || lecture.getLikeCount() <= 0) {
      throw new CustomException(ExceptionCode.CANNOT_DECREASE_LIKE_COUNT);
    }
    lecture.decreaseLikeCount(); // TODO: 직접 카운팅.
    lectureLikeRepository.delete(lectureLike);
  }

  private Lecture findLectureById(Long lectureId) {
    return lectureRepository.findById(lectureId)
        .orElseThrow(() -> new CustomException(ExceptionCode.LECTURE_NOT_FOUND));
  }

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
  }

}
