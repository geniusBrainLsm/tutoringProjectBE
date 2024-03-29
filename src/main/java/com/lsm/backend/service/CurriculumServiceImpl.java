package com.lsm.backend.service;

import com.lsm.backend.model.Course;
import com.lsm.backend.payload.CurriculumDTO;
import com.lsm.backend.repository.CourseRepository;
import com.lsm.backend.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class CurriculumServiceImpl implements CurriculumService{
    private final CurriculumRepository curriculumRepository;
    private final CourseRepository courseRepository;


    @Override
    public CurriculumDTO createCurriculum(List<CurriculumDTO> curriculumDTOS ,Long courseId) {
        Course course = courseRepository.findById(course);
        List<CurriculumDTO> curriculum = new ArrayList<>();

        for(CurriculumDTO curriculumDTO : ){

        }

        return CurriculumDTO.fromEntity()
    }

    @Override
    public CurriculumDTO updateCurriculum(CurriculumDTO curriculumDTO) {
        return null;
    }

    @Override
    public List<CurriculumDTO> getAllCurriculum() {
        return null;
    }

    @Override
    public Optional<CurriculumDTO> getCurriculum(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteCurriculum(Long id) {

    }
}
