package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Course;
import com.lsm.backend.model.Curriculum;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CourseDTO;
import com.lsm.backend.payload.CurriculumDTO;
import com.lsm.backend.repository.CourseRepository;
import com.lsm.backend.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;
    @Override
    public CourseDTO createaCourse(CourseDTO courseDTO, List<CurriculumDTO> curriculumDTOS) {
        Course course = courseRepository.save(courseDTO.toEntity());

        List<Curriculum> curricula = new ArrayList<>();
        for (CurriculumDTO curriculumDTO : curriculumDTOS) {
            Curriculum curriculum = curriculumDTO.toEntity();
            curriculum.setCourse(course);
            curricula.add(curriculumRepository.save(curriculum));
        }
        CourseDTO savedCourseDTO =  CourseDTO.fromEntity(course);

        List<CurriculumDTO> savedCurriculumDTOS = new ArrayList<>();

        for(Curriculum curriculum : curricula){
            savedCurriculumDTOS.add(CurriculumDTO.fromEntity(curriculum));
        }
        //커리큘럼(묶어서) course에 전달
        savedCourseDTO.setCurricula(savedCurriculumDTOS);

        return savedCourseDTO;
    }

    @Override
    public Optional<CourseDTO> getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Course", "",id));
        return Optional.of(CourseDTO.fromEntity(course));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();

        for(Course course : courses){
            CourseDTO courseDTO = CourseDTO.fromEntity(course);
            courseDTOList.add(courseDTO);
        }
        return courseDTOList;
    }


    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(()->new ResourceNotFoundException("Course","asd",courseDTO.getId()));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setThumbnailUrl(courseDTO.getTitle());
        course.setInstructorName(courseDTO.getInstructorName());

        course.setCurricula(courseDTO.getCurricula());
        course.setUpdateHistories(courseDTO.getUpdateHistories());

        course = courseRepository.save(course);
        return CourseDTO.fromEntity(course);
    }
}
