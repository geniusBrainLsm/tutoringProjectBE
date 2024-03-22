package com.lsm.backend.controller;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardServiceImpl boardService;
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody BoardDTO boardDTO){
        BoardDTO createdDTO = boardService.createPost(boardDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        boardService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<BoardDTO> board = boardService.getPost(id);

        if(board.isPresent()) {
            return new ResponseEntity<>(board.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllPost(){
        List<BoardDTO> posts = boardService.getAllPost();
        return ResponseEntity.ok(posts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
        BoardDTO createdDTO = boardService.updatePost(boardDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
}
