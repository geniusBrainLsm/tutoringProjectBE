package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@RequiredArgsConstructor
@Setter
@Table(name="board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardType;

    private String title;

    private String writer;

    private String contents;
    @ManyToMany
    @JoinColumn(name = "board_id")
    private List<Tag> tag;

    private Long likeCount;

    private Long viewCounter;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

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
