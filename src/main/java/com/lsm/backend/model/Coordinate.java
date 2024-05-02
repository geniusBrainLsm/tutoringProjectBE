package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "coordinates")
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long x0;
    private Long y0;
    private Long x1;
    private Long y1;
    private String color;
    private String username;
    private Long lineWidth;
    //private String instrument;

    @ManyToOne
    @JoinColumn(name = "drawing_id", nullable = false)
    private Drawing drawing;
}