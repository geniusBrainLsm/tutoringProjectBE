package com.lsm.backend.websocket;

import com.lsm.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
@RequiredArgsConstructor
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    private final TokenProvider tokenProvider;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map attributes) throws Exception {

        // 토큰 검증 등 수행
        String token = (String) request.getHeaders().getFirst("Authorization");


        if(!tokenProvider.validateToken(token)) {
            return false;
        }

        // 토큰 검증 성공 시 attributes에 정보 저장
        attributes.put("userId", tokenProvider.getUserIdFromToken(token));

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception)
    {

    }

}