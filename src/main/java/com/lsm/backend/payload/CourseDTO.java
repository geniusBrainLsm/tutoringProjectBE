package com.lsm.backend.payload;
import com.lsm.backend.model.Course;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String instructorName;
    private String description;
    private List<CurriculumDTO> curricula = new ArrayList<>();
    private List<CourseUpdateHistoryDTO> updateHistories = new ArrayList<>();



//    public CourseDTO(Course course) {
//        this.id = course.getId();
//        this.title = course.getTitle();
//        this.thumbnailUrl = course.getThumbnailUrl();
//        this.instructorName = course.getInstructorName();
//        this.description = course.getDescription();
//        this.curricula = course.getCurricula().stream()
//                .map(CurriculumDTO::new)
//                .collect(Collectors.toList());
////        this.updateHistories = course.getUpdateHistories().stream()
////                .map(CourseUpdateHistoryDTO::new)
////                .collect(Collectors.toList());
//    }

    public Course toEntity() {
        Course course = new Course();
        course.setId(this.id);
        course.setTitle(this.title);
        course.setThumbnailUrl(this.thumbnailUrl);
        course.setInstructorName(this.instructorName);
        course.setDescription(this.description);
        course.setCurricula(this.curricula.stream()
                .map(CurriculumDTO::toEntity)
                .collect(Collectors.toList()));
        course.setUpdateHistories(this.updateHistories.stream()
                .map(CourseUpdateHistoryDTO::toEntity)
                .collect(Collectors.toList()));
        return course;
    }

    public static CourseDTO fromEntity(Course course){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setThumbnailUrl(course.getThumbnailUrl());
        courseDTO.setInstructorName(course.getInstructorName());
        courseDTO.setDescription(course.getDescription());
        return courseDTO;
    }
}