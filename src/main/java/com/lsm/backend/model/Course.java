package com.lsm.backend.model;
import com.lsm.backend.model.Curriculum;
import com.lsm.backend.model.CourseUpdateHistory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String thumbnailUrl;
    private String instructorName;
    private String description;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Curriculum> curricula = new ArrayList<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseUpdateHistory> updateHistories = new ArrayList<>();
}