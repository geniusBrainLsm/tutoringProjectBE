package com.lsm.backend.websocket;

import com.lsm.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(ChatHandler.class);

    private Map<String, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 소켓 연결성공 시
        sessions.put(session.getId(), session);
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        TextMessage echoMsg = new TextMessage("Echo : " + payload);
        session.sendMessage(echoMsg);
        log.info("Received: " + message.getPayload());

    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }


}
