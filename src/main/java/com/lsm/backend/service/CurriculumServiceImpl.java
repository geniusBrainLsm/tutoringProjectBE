package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Course;
import com.lsm.backend.model.Curriculum;
import com.lsm.backend.payload.course.CurriculumDTO;
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
    public List<CurriculumDTO> createCurriculum(List<CurriculumDTO> curriculumDTOS, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        List<CurriculumDTO> curriculumDTOList = new ArrayList<>();

        for (CurriculumDTO curriculumDTO : curriculumDTOS) {
            Curriculum curriculum = curriculumDTO.toEntity();
            curriculum.setCourse(course);
            Curriculum savedCurriculum = curriculumRepository.save(curriculum);
            curriculumDTOList.add(CurriculumDTO.fromEntity(savedCurriculum));
        }

        return curriculumDTOList;
    }

    @Override
    public CurriculumDTO updateCurriculum(CurriculumDTO curriculumDTO) {
        Curriculum curriculum = curriculumRepository.findById(curriculumDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", curriculumDTO.getId()));

        curriculum.setTitle(curriculumDTO.getTitle());
        curriculum.setVideoUrl(curriculumDTO.getVideoUrl());

        Curriculum updatedCurriculum = curriculumRepository.save(curriculum);

        return CurriculumDTO.fromEntity(updatedCurriculum);
    }

    @Override
    public List<CurriculumDTO> getAllCurriculum() {
        List<CurriculumDTO> curriculumDTOList = new ArrayList<>();
        List<Curriculum> curricula = curriculumRepository.findAll();

        for (Curriculum curriculum : curricula) {
            curriculumDTOList.add(CurriculumDTO.fromEntity(curriculum));
        }

        return curriculumDTOList;
    }

    @Override
    public Optional<CurriculumDTO> getCurriculum(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum", "id", id));

        return Optional.of(CurriculumDTO.fromEntity(curriculum));
    }

    @Override
    public void deleteCurriculum(Long id) {
        curriculumRepository.deleteById(id);
    }
}
