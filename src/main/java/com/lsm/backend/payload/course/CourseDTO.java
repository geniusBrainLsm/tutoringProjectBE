package com.lsm.backend.payload.course;

import com.lsm.backend.model.Course;
import com.lsm.backend.payload.board.ImageDTO;
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
    private List<CourseFieldDTO> field;
    private String thumbnailUrl;
    private String instructorName;
    private String description;
    private List<CurriculumDTO> curricula = new ArrayList<>();
    private List<CourseUpdateHistoryDTO> updateHistories = new ArrayList<>();
    private List<ImageDTO> images = new ArrayList<>();

    public Course toEntity() {
        Course course = Course.builder()
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

        if (images != null) {
            course.setImages(images.stream()
                    .map(ImageDTO::toEntity)
                    .collect(Collectors.toList()));
        }

        return course;
    }

    public static CourseDTO fromEntity(Course course) {
        CourseDTO courseDTO = CourseDTO.builder()
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
                .images(course.getImages() != null ? course.getImages().stream()
                        .map(ImageDTO::fromEntity)
                        .collect(Collectors.toList()) : new ArrayList<>())
                .build();

        return courseDTO;
    }
}
