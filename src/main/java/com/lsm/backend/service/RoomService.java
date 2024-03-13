package com.lsm.backend.service;

import com.lsm.backend.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room save(Room room);

    Room update(Room room);

    void deleteRoom(Long roomId);

    Optional<Room> getRoom(Long roomId);

    List<Room> getAllRooms();

}
