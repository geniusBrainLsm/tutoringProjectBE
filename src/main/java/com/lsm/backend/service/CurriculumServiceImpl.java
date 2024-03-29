//package com.lsm.backend.service;
//
//import com.lsm.backend.exception.ResourceNotFoundException;
//import com.lsm.backend.model.Course;
//import com.lsm.backend.model.Curriculum;
//import com.lsm.backend.payload.CourseDTO;
//import com.lsm.backend.payload.CurriculumDTO;
//import com.lsm.backend.repository.CourseRepository;
//import com.lsm.backend.repository.CurriculumRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//@RequiredArgsConstructor
//@Service
//public class CurriculumServiceImpl implements CurriculumService{
//    private final CurriculumRepository curriculumRepository;
//    private final CourseRepository courseRepository;
//
//
//    @Override
//    public List<Curriculum> createCurriculum(List<CurriculumDTO> curriculumDTOS ,Long courseId) {
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(()->new ResourceNotFoundException("course","id",course.getId()));
//
//        List<Curriculum> curricula = new ArrayList<>();
//
//        for(CurriculumDTO curriculumDTO : curriculumDTOS){
//            Curriculum curriculum = new CurriculumDTO().toEntity();
//            curriculum.setCourse(course);
//            curricula.add(curriculumRepository.save(curriculum));
//        }
//
//        return curricula;
//    }
//
//    @Override
//    public CurriculumDTO updateCurriculum(CurriculumDTO curriculumDTO) {
//        return null;
//    }
//
//    @Override
//    public List<CurriculumDTO> getAllCurriculum() {
//        return null;
//    }
//
//    @Override
//    public Optional<CurriculumDTO> getCurriculum(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void deleteCurriculum(Long id) {
//
//    }
//}
