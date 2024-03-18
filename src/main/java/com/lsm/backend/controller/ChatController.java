package com.lsm.backend.controller;

import com.lsm.backend.model.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/app/chatSendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {

        logger.info("Received chat message: {}", chatMessage.getContent());
        messagingTemplate.convertAndSend(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/app/chatAddUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessage) {

        logger.info("joined", chatMessage);

        return chatMessage;
    }


}
