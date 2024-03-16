package com.lsm.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Drawings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore//순환참조방지
    private Room room;

    @OneToMany(mappedBy="drawings", cascade = CascadeType.ALL)
    List<Coordinates> coordinates;
}
