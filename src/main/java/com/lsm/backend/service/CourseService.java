package com.lsm.backend.service;

import com.lsm.backend.model.Course;
import com.lsm.backend.payload.CourseDTO;
import com.lsm.backend.payload.CurriculumDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    CourseDTO createaCourse(CourseDTO courseDTO, List<CurriculumDTO> curriculumDTOS);

    Optional<CourseDTO> getCourse(Long id);
    List<CourseDTO> getAllCourses();
    void deleteCourse(Long id);
    CourseDTO updateCourse(CourseDTO courseDTO);



}
