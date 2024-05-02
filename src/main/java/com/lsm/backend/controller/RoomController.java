package com.lsm.backend.controller;

import com.lsm.backend.model.Coordinate;
import com.lsm.backend.model.Drawing;
import com.lsm.backend.payload.DrawingLineDTO;
import com.lsm.backend.repository.DrawingRepository;
import com.lsm.backend.service.DrawingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final DrawingRepository drawingRepository;
    private final DrawingService drawingService;

//    @MessageMapping("/rooms/{roomId}/chat")
//    @SendTo("/topic/rooms/{roomId}/chat")
//    public ChatMessage handleChatMessage(@DestinationVariable String roomId, ChatMessage chatMessage) {
//        // 채팅 메시지 처리 로직 추가 (예: 데이터베이스에 저장)
//        return chatMessage;
//    }

    @MessageMapping("/rooms/{roomId}/drawings")
    @SendTo("/topic/rooms/{roomId}/drawings")
    public Coordinate handleDrawingLine(@DestinationVariable String roomId, DrawingLineDTO drawingLineDTO) {
        // 그리기 작업 처리 로직 추가
        Coordinate coordinate = new Coordinate();
        coordinate.setX0(drawingLineDTO.getX0());
        coordinate.setY0(drawingLineDTO.getY0());
        coordinate.setX1(drawingLineDTO.getX1());
        coordinate.setY1(drawingLineDTO.getY1());
        coordinate.setColor(drawingLineDTO.getColor());
        coordinate.setLineWidth(drawingLineDTO.getLineWidth());
        coordinate.setUsername(drawingLineDTO.getUsername());

        Drawing drawing = drawingService.saveDrawingLine(roomId, coordinate);
        coordinate.setDrawing(drawing);

        return coordinate;
    }


}