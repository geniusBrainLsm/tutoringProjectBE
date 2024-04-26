package com.lsm.backend.model;
import com.lsm.backend.model.Curriculum;
import com.lsm.backend.model.CourseUpdateHistory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    //썸네일이미지
    private String thumbnailUrl;
    private String instructorName;
    private String description;
    //강의영상 커리큘럼 영상리스트
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Curriculum> curricula = new ArrayList<>();
    //업데이트내역
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseUpdateHistory> updateHistories = new ArrayList<>();

    @Column(name = "created_at")

    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}