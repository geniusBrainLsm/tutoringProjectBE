package com.lsm.backend.payload;

import com.lsm.backend.model.Room;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String title;
    private String owner;
    private LocalDateTime createdDate;

    public Room toEntity(){
        Room room = new Room();
        room.setId(this.id);
        room.setTitle(this.title);
        room.setOwner(this.owner);
        room.setCreatedDate(this.createdDate);

        return room;
    }

    public static RoomDTO fromEntity(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setTitle(room.getTitle());
        roomDTO.setOwner(room.getOwner());
        roomDTO.setCreatedDate(room.getCreatedDate());

        return roomDTO;
    }
}
