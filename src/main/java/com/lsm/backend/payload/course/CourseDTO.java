package com.lsm.backend.payload.course;
import com.lsm.backend.model.Course;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String instructorName;
    private String description;
    private List<CurriculumDTO> curricula = new ArrayList<>();
    private List<CourseUpdateHistoryDTO> updateHistories = new ArrayList<>();


    public Course toEntity() {
        return Course.builder()
                .id(id)
                .title(title)
                .thumbnailUrl(thumbnailUrl)
                .instructorName(instructorName)
                .description(description)
                .curricula(curricula.stream()
                        .map(CurriculumDTO::toEntity)
                        .collect(Collectors.toList()))
                .updateHistories(updateHistories != null ? updateHistories.stream()
                        .map(CourseUpdateHistoryDTO::toEntity)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public static CourseDTO fromEntity(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .thumbnailUrl(course.getThumbnailUrl())
                .instructorName(course.getInstructorName())
                .description(course.getDescription())
                .curricula(course.getCurricula().stream()
                        .map(CurriculumDTO::fromEntity)
                        .collect(Collectors.toList()))
                .updateHistories(course.getUpdateHistories() != null ? course.getUpdateHistories().stream()
                        .map(CourseUpdateHistoryDTO::fromEntity)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }
}