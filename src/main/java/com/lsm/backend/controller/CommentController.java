package com.lsm.backend.controller;

import com.lsm.backend.model.Comment;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentServiceImpl commentService;
    public ResponseEntity<?> createComment(CommentDTO commentDTO){
        CommentDTO createdDTO = commentService.createComment(commentDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getComment(@PathVariable Long id) {
        Optional<CommentDTO> comment = commentService.getComment(id);

        if(comment.isPresent()) {
            return ResponseEntity.ok(comment);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<CommentDTO>> getAllComment(){
        List<CommentDTO> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    }

    public ResponseEntity<?> editComment(@RequestBody CommentDTO commentDTO){
        CommentDTO createdDTO = commentService.createComment(commentDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
}
