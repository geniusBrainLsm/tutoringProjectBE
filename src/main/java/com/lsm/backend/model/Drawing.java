package com.lsm.backend.model;

import com.lsm.backend.payload.Coordinates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Drawing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Transient
    private List<Coordinates> line;
}
