package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.Message;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="room")
@Getter
@Setter

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="owner", nullable = true)
    private String owner;

    @Column(name="created_date",nullable = true)
    private LocalDateTime createdDate;

}
