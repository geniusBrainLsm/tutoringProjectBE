package com.lsm.backend.model;

import com.lsm.backend.payload.DrawingLineDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "drawings")
public class Drawing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "drawing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coordinate> line;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}