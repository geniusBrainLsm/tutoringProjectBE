package com.lsm.backend.payload.course;

import com.lsm.backend.model.Curriculum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurriculumDTO {
    private Long id;

    private String title;

    private String videoUrl;

    public Curriculum toEntity(){
        Curriculum curriculum = new Curriculum();
        curriculum.setId(this.id);
        curriculum.setTitle(this.title);
        curriculum.setVideoUrl(this.videoUrl);
        return curriculum;
    }

    public static CurriculumDTO fromEntity(Curriculum curriculum){
        CurriculumDTO curriculumDTO = new CurriculumDTO();
        curriculumDTO.setId(curriculum.getId());
        curriculumDTO.setVideoUrl(curriculum.getVideoUrl());
        curriculumDTO.setTitle(curriculum.getTitle());
        return curriculumDTO;
    }
}
