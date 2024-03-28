package com.lsm.backend.service;

import com.lsm.backend.model.Course;
import com.lsm.backend.payload.CourseDTO;
import com.lsm.backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    @Override
    public CourseDTO createaCourse(CourseDTO courseDTO) {
        Course course = courseRepository.save(courseDTO.toEntity());
        return CourseDTO.fromEntity(course);
    }

    @Override
    public Optional<CourseDTO> getCourse(Long id) {

    }

    @Override
    public Page<CourseDTO> getAllCourses(Pageable pageable) {

    }

    @Override
    public Optional<CourseDTO> getCourse(CourseDTO courseDTO) {

    }

    @Override
    public void deleteCourse(Long id) {

    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {

    }
}
