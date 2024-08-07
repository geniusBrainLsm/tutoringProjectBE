package com.lsm.backend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import com.lsm.backend.model.Image;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinTable(
            name = "board_tag",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Nullable
    private List<Tag> tag;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<Image> image;

    private Long likeCount;

    private Long viewCount;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @OrderBy("id asc")
    private List<Comment> comment;

    @Transient //이거 그냥 갯수세기용임 영속성데이터아님
    private Long commentsCount;

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
