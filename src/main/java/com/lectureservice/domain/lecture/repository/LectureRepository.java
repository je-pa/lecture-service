package com.lectureservice.domain.lecture.repository;

import com.lectureservice.domain.lecture.entity.Lecture;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>,
    JpaSpecificationExecutor<Lecture> {

  Page<Lecture> findAll(@Nullable Specification<Lecture> spec, Pageable pageable);

}
