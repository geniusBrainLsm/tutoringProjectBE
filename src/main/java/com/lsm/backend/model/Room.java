package com.lsm.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    private String roomName;
    private int roomMaxUsers;
    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;
}
