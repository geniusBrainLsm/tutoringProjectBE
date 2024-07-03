package com.lsm.backend.repository;

import com.lsm.backend.model.CourseField;
import com.lsm.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseFieldRepository extends JpaRepository<CourseField, Long> {
    Optional<CourseField> findByContents(String contents);
}
