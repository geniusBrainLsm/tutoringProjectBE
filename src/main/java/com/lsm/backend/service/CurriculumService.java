package com.lsm.backend.service;

import com.lsm.backend.payload.course.CurriculumDTO;

import java.util.List;
import java.util.Optional;

public interface CurriculumService {
    List<CurriculumDTO> createCurriculum(List<CurriculumDTO> curriculumDTOS, Long courseId);
    CurriculumDTO updateCurriculum(CurriculumDTO curriculumDTO);

    List<CurriculumDTO> getAllCurriculum();

    Optional<CurriculumDTO> getCurriculum(Long id);

    void deleteCurriculum(Long id);
}
