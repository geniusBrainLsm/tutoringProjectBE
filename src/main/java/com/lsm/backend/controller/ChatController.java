package com.lsm.backend.controller;

import com.lsm.backend.payload.ChatMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import static com.lsm.backend.payload.ChatMessageDTO.MessageType.CHAT;

@RestController
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @MessageMapping("/app/sendchat")
//    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
//        logger.info("Received message from client: " + chatMessageDTO);
//        messagingTemplate.convertAndSend("/topic/public", chatMessageDTO);
//    }
//    @MessageMapping("/app/adduser")
//    public void addUser(@Payload ChatMessageDTO chatMessageDTO) {
//        logger.info("joined", chatMessageDTO);
//        messagingTemplate.convertAndSend("/topic/public", chatMessageDTO); // 메시지를 /topic/public으로 전송
//    }

    @MessageMapping("/app/sendchat")
    public ChatMessageDTO sendChat(@Payload ChatMessageDTO chatMessageDTO) {
        messagingTemplate.convertAndSend("/topic/public", chatMessageDTO);

        return chatMessageDTO;
    }

}
