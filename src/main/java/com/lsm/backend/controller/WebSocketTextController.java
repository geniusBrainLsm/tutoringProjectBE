package com.lsm.backend.controller;

import com.lsm.backend.payload.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WebSocketTextController {

    final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send/{roomId}")
    @SendTo("/topic/{roomId}")
    public Coordinates sendMessage(@Payload Coordinates coordinates) {
        return coordinates;
    }
}
