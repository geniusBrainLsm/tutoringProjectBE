package com.lsm.backend.payload.course;

import com.lsm.backend.model.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseFieldDTO {
    private Long id;
    private String contents;
    private List<Course> courses;
}
