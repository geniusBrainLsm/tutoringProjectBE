package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.messaging.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String address;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Drawing> drawings;
}