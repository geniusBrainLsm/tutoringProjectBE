package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Course;
import com.lsm.backend.model.CourseUpdateHistory;
import com.lsm.backend.model.Curriculum;
import com.lsm.backend.model.Tag;
import com.lsm.backend.payload.course.CourseDTO;
import com.lsm.backend.payload.course.CourseUpdateHistoryDTO;
import com.lsm.backend.payload.course.CurriculumDTO;
import com.lsm.backend.repository.CourseRepository;

import com.lsm.backend.repository.CourseUpdateHistoryRepository;
import com.lsm.backend.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;
    private final CourseUpdateHistoryRepository courseUpdateHistoryRepository;
    @Override
    public CourseDTO createaCourse(CourseDTO courseDTO) {
        // 1. 강의 엔티티 생성 및 저장
        Course course = courseRepository.save(courseDTO.toEntity());

        // 2. 커리큘럼 엔티티 생성 및 저장
        List<Curriculum> curricula = courseDTO.getCurricula().stream()
                .map(curriculumDTO -> {
                    Curriculum curriculum = curriculumDTO.toEntity();
                    curriculum.setCourse(course); // 커리큘럼에 강의 정보 설정
                    return curriculumRepository.save(curriculum);
                })
                .collect(Collectors.toList());

        // 3. 업데이트 이력 엔티티 생성 및 저장 (없을 경우 건너뛰기)
        List<CourseUpdateHistory> updateHistories = Optional.ofNullable(courseDTO.getUpdateHistories())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(historyDTO -> {
                    CourseUpdateHistory history = historyDTO.toEntity();
                    history.setCourse(course); // 업데이트 이력에 강의 정보 설정
                    return courseUpdateHistoryRepository.save(history);
                })
                .collect(Collectors.toList());

        // 4. 강의 엔티티에 커리큘럼과 업데이트 이력 설정
        course.setCurricula(curricula);
        course.setUpdateHistories(updateHistories);

        // 5. 강의 DTO 생성 및 반환
        return CourseDTO.fromEntity(course);
    }

    @Override
    public Optional<CourseDTO> getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Course", "",id));
        return Optional.of(CourseDTO.fromEntity(course));
    }

    @Override
    public Page<CourseDTO> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id")));
        return courses.map(CourseDTO::fromEntity);
    }


    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseDTO.getId()));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setThumbnailUrl(courseDTO.getThumbnailUrl());
        course.setInstructorName(courseDTO.getInstructorName());

        // CourseDTO의 curricula를 Course 엔티티의 타입g으로 변환하여 설정
        List<Curriculum> curriculaEntities = new ArrayList<>();
        for (CurriculumDTO curriculumDTO : courseDTO.getCurricula()) {
            curriculaEntities.add(curriculumDTO.toEntity());
        }
        course.setCurricula(curriculaEntities);

        // CourseDTO의 updateHistories를 Course 엔티티의 타입으로 변환하여 설정
        List<CourseUpdateHistory> updateHistoryEntities = new ArrayList<>();
        for (CourseUpdateHistoryDTO updateHistoryDTO : courseDTO.getUpdateHistories()) {
            updateHistoryEntities.add(updateHistoryDTO.toEntity());
        }
        course.setUpdateHistories(updateHistoryEntities);

        course = courseRepository.save(course);
        return CourseDTO.fromEntity(course);
    }

}
