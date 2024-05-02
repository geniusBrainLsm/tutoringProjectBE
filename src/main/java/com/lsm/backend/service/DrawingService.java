package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Coordinate;
import com.lsm.backend.model.Drawing;
import com.lsm.backend.model.Room;
import com.lsm.backend.repository.DrawingRepository;
import com.lsm.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class DrawingService {
    private final DrawingRepository drawingRepository;
    private final RoomRepository roomRepository;

    public DrawingService(DrawingRepository drawingRepository, RoomRepository roomRepository) {
        this.drawingRepository = drawingRepository;
        this.roomRepository = roomRepository;
    }

    public Drawing saveDrawingLine(String roomId, Coordinate coordinate) {
        Room room = roomRepository.findById(Long.parseLong(roomId))
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        Drawing drawing = room.getDrawings().stream()
                .findFirst()
                .orElseGet(() -> {
                    Drawing newDrawing = new Drawing();
                    newDrawing.setRoom(room);
                    return drawingRepository.save(newDrawing);
                });

        coordinate.setDrawing(drawing);
        return drawing;
    }
}

