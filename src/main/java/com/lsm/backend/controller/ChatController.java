package com.lsm.backend.controller;

import com.lsm.backend.model.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/app/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {

        logger.info("Received chat message: {}", chatMessage);

        return chatMessage;
    }

    @MessageMapping("/app/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessage) {

        logger.info("joined", chatMessage);

        return chatMessage;
    }


}
