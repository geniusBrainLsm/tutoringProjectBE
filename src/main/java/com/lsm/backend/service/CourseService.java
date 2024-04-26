package com.lsm.backend.service;

import com.lsm.backend.payload.course.CourseDTO;
import com.lsm.backend.payload.course.CurriculumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    CourseDTO createaCourse(CourseDTO courseDTO);

    Optional<CourseDTO> getCourse(Long id);
    Page<CourseDTO> getAllCourses(Pageable pageable);
    void deleteCourse(Long id);
    CourseDTO updateCourse(CourseDTO courseDTO);

}
