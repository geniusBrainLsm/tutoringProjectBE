package com.lsm.backend.controller;

        import com.lsm.backend.model.Room;
        import com.lsm.backend.payload.ApiResponse;
        import com.lsm.backend.repository.RoomRepository;
        import jakarta.validation.Valid;
        import lombok.RequiredArgsConstructor;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import com.lsm.backend.payload.CreateRoomRequest;
        import com.lsm.backend.payload.ApiResponse;
@RestController
@RequiredArgsConstructor
public class RoomController {
    //주입할거
    private final RoomRepository roomRepository;
    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest){
        Room room = new Room();
        room.setRoomName(createRoomRequest.getRoomName());
        room.setRoomMaxUsers(room.getRoomMaxUsers());
        roomRepository.save(room);
        return ResponseEntity.ok(new ApiResponse(true, "room create successfully"));
    }
}
