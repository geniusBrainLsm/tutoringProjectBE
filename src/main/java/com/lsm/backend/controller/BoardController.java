package com.lsm.backend.controller;

import com.lsm.backend.model.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    public ResponseEntity<Board> addPost(@RequestBody BoardDTO boardDTO){
        boardService.createBoard(boardDTO);
        return ResponseEntity.status(201).build();

    }
}
