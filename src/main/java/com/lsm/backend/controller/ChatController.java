package com.lsm.backend.controller;

import com.lsm.backend.service.MessagingService;
import com.lsm.backend.websocket.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final MessagingService messagingService;

    @MessageMapping("/chat/")
    public void handleChat(Message message) throws Exception {
        System.out.println(message);
    }

}