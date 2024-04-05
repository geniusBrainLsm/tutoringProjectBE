package com.lsm.backend.controller;

import com.lsm.backend.model.Comment;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentServiceImpl commentService;
    @PostMapping
    public ResponseEntity<?> createComment(CommentDTO commentDTO, Long id){
        CommentDTO createdDTO = commentService.createComment(commentDTO, id);
        return ResponseEntity.status(201).body(createdDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        Optional<CommentDTO> comment = commentService.getComment(id);

        if(comment.isPresent()) {
            return ResponseEntity.ok(comment);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComment(){
        List<CommentDTO> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    }
    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id){
        CommentDTO createdDTO = commentService.updateComment(commentDTO, id);
        return ResponseEntity.status(201).body(createdDTO);
    }
}
