package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Setter
@RequiredArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "contents")
    private String contents;
}