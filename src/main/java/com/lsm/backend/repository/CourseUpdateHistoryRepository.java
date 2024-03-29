package com.lsm.backend.repository;

import com.lsm.backend.model.CourseUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseUpdateHistoryRepository extends JpaRepository<CourseUpdateHistory, Long> {
}
