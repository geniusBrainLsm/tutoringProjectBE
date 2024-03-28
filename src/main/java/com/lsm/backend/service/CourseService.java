package com.lsm.backend.service;

import com.lsm.backend.model.Course;
import com.lsm.backend.payload.CourseDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface CourseService {
    CourseDTO createaCourse(CourseDTO courseDTO);

    Optional<CourseDTO> getCourse(Long id);

    Page<Course> getAllCourses(Pageable pageable);

}
