package com.lsm.backend.controller;

import com.lsm.backend.payload.board.CommentDTO;
import com.lsm.backend.payload.board.CommentRequestDTO;
import com.lsm.backend.security.CurrentUser;
import com.lsm.backend.security.UserPrincipal;
import com.lsm.backend.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentServiceImpl commentService;
    @PostMapping("/board/{id}/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentDTO, @CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        //commentService.createComment(commentDTO, userPrincipal, id);
        return ResponseEntity.status(201).body(commentService.createComment(commentDTO, userPrincipal, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id){
        CommentDTO createdDTO = commentService.updateComment(commentDTO, id);
        return ResponseEntity.status(201).body(createdDTO);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getComment(@PathVariable Long id) {
//        Optional<CommentDTO> comment = commentService.getComment(id);
//
//        if(comment.isPresent()) {
//            return ResponseEntity.ok(comment);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @GetMapping
//    public ResponseEntity<List<CommentDTO>> getAllComment(){
//        List<CommentDTO> comments = commentService.getAllComment();
//        return ResponseEntity.ok(comments);
//    }
}
