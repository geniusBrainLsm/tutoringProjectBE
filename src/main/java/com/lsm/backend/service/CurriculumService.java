package com.lsm.backend.service;

import com.lsm.backend.model.Curriculum;
import com.lsm.backend.payload.CurriculumDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CurriculumService {
    CurriculumDTO createCurriculum(List<CurriculumDTO> curriculumDTOs, Long courseId);

    CurriculumDTO updateCurriculum(CurriculumDTO curriculumDTO);

    List<CurriculumDTO> getAllCurriculum();

    Optional<CurriculumDTO> getCurriculum(Long id);

    void deleteCurriculum(Long id);
}
