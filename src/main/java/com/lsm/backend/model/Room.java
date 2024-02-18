package com.lsm.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String owner;

    private int currentCount;

    private int maxUsers;

    @OneToMany(mappedBy="room")
    private List<Message> messages;

    @OneToMany
    @JoinColumn(name="room_id")
    private List<User> users;
    public void addUser(User user) {
        users.add(user);
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
