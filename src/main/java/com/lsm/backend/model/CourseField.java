package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "course_field", indexes = {@Index(name = "idx_field_id", columnList = "id")})

public class CourseField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contents")
    private String contents;

    @ManyToMany(mappedBy = "fields", cascade = CascadeType.ALL)
    private List<Course> courses;
}
