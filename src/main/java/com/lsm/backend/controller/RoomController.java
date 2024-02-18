package com.lsm.backend.controller;

import com.lsm.backend.exception.RoomNotFoundException;
import com.lsm.backend.exception.UserNotFoundException;
import com.lsm.backend.model.Room;
import com.lsm.backend.model.User;
import com.lsm.backend.payload.ApiResponse;
import com.lsm.backend.repository.RoomRepository;
import com.lsm.backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lsm.backend.payload.CreateRoomRequest;
import com.lsm.backend.payload.ApiResponse;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {
    //주입할거
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    @PostMapping("/rooms")
    public Room createRoom(@RequestBody CreateRoomRequest request){
            Room room = new Room();
            room.setTitle(request.getTitle());
            room.setMaxUsers(request.getMaxUsers());

            roomRepository.save(room);
            return room;
    }
    @PutMapping("/rooms/{roomId}/enter")  //update느낌임
    public ResponseEntity<?> enterRoom(@PathVariable Long roomId, Long userId){
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        Optional<User> optionalUser = userRepository.findById(userId);

        Room room = optionalRoom.orElseThrow(() -> new RoomNotFoundException());
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException());

        // 방 입장 로직
        room.addUser(user);

        roomRepository.save(room);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));

    }
}
