package com.lsm.backend.controller;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardServiceImpl boardService;
    public ResponseEntity<?> addPost(@RequestBody BoardDTO boardDTO){
        boardService.createPost(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // HTTP 상태 코드 직접 지정

        //별도의 body없을시 build()
    }

    public ResponseEntity<?> deletePost(@PathVariable Long id){
        boardService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<Board> board = boardService.getPost(id);

        if(board.isPresent()) {
            return new ResponseEntity<>(board.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Board>> getAllPost(){
        List<Board> posts = boardService.getAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
