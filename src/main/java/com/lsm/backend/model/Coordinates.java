package com.lsm.backend.model;

import com.lsm.backend.model.Drawings;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="drawing_id")
    private Drawings drawings;

    private Long x0;

    private Long y0;

    private Long x1;

    private Long y1;
}