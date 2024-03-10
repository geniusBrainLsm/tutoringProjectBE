package com.lsm.backend.socket;

import com.alibaba.fastjson.JSON;
import com.lsm.backend.security.CustomUserDetailsService;
import com.lsm.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.alibaba.fastjson.JSONObject;
@Component
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {
    private final TokenProvider tokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final CustomUserDetailsService customUserDetailsService;
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        JSONObject json = (JSONObject) JSON.parse(message.getPayload());

        if(json.getString("type").equals("auth")) {
            String jwt = json.getString("token");
            try{
                tokenProvider.validateToken(jwt);
                Long userId = tokenProvider.getUserIdFromToken(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext ().setAuthentication(authentication);
            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }


        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 웹소켓 연결 종료했을 때 처리
    }
}
