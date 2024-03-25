package com.lsm.backend.controller;

import com.lsm.backend.payload.ChatMessageDTO;
import com.lsm.backend.payload.DrawMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/app/chat")
    @SendTo("/topic/public")
    public ChatMessageDTO sendChat(@Payload ChatMessageDTO chatMessageDTO) {
        logger.info("chat");
        return chatMessageDTO;
    }

    @MessageMapping("/app/draw")
    @SendTo("/topic/public")
    public DrawMessageDTO sendDraw(@Payload DrawMessageDTO drawMessageDTO) {
        logger.info("draw");
        return drawMessageDTO;
    }

}
