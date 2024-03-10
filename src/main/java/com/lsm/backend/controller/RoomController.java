package com.lsm.backend.controller;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Room;
import com.lsm.backend.repository.RoomRepository;
import com.lsm.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;



    @PostMapping
    public Room createRoom(@RequestBody Room room){
        return roomRepository.save(room);
    }
    @GetMapping
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable Long id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("없어용"));

    }

    //삭제의 경우 return값이 없으니까 ResponseEntity.(성공,실패만 가리기 위해 httpstatus로 200인지 아닌지)
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
}
