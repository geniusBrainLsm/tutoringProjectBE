package com.lsm.backend.controller;

import com.lsm.backend.payload.RoomDTO;
import com.lsm.backend.repository.UserRepository;
import com.lsm.backend.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomService;
    private final UserRepository userRepository;



    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomDTO roomDTO){
        RoomDTO createdDTO = roomService.createRoom(roomDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }


    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms(){
        List<RoomDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id){
        Optional<RoomDTO> room = roomService.getRoom(id);
        if (room.isPresent()) {
            return ResponseEntity.ok(room);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //삭제의 경우 return값이 없다.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
        //build는 body에 보낼거없을때쓰는 메서드
    }
}
