package com.lsm.backend.service;

import com.lsm.backend.model.Room;
import com.lsm.backend.payload.RoomDTO;
import com.lsm.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = roomRepository.save(roomDTO.toEntity());
        return RoomDTO.fromEntity(room);
    }


    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Optional<RoomDTO> getRoom(Long id) {
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new Exception("없어용."));

            return Optional.of(RoomDTO.fromEntity(room));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();

        for(Room room: rooms){
            RoomDTO roomDTO = RoomDTO.fromEntity(room);
            roomDTOList.add(roomDTO);
        }
        return roomDTOList;
    }
}
