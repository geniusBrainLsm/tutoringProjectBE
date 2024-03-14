package com.lsm.backend.controller;

import com.lsm.backend.exception.ResourceNotFoundException;

import com.lsm.backend.model.Drawings;
import com.lsm.backend.model.Room;
import com.lsm.backend.repository.RoomRepository;
import com.lsm.backend.security.TokenProvider;
import com.lsm.backend.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomServiceImpl roomService;



    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        roomService.save(room);
        return ResponseEntity.ok(room);
    }
    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id){
        Optional<Room> room = roomService.getRoom(id);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //삭제의 경우 return값이 없으니까 ResponseEntity.(성공,실패만 가리기 위해 httpstatus로 200인지 아닌지)
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @PostMapping("/{roomId}/save-drawings")
    public ResponseEntity<String> saveDrawing(@PathVariable Long roomId, @RequestBody Drawings drawings){
        log.info("Save drawing - Room ID: {}, Drawing: {}", roomId, drawings);

        Optional<Room> roomOptional = roomService.getRoom(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();


            room.getDrawings().add(drawings);

            roomService.update(room);

            log.info("Drawing added and room updated");

            return ResponseEntity.ok("Drawing saved successfully!");
        } else {
            return ResponseEntity.badRequest().body("Room not found");
        }
    }

    @GetMapping("/{roomId}/get-drawings")
    public ResponseEntity<List<Drawings>> getDrawings(@PathVariable Long roomId) {
        Optional<Room> roomOptional = roomService.getRoom(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            log.info("Drawing List: {}", room.getDrawings().toString());
            return ResponseEntity.ok(room.getDrawings());


        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
