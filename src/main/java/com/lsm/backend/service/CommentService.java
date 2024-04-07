package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.payload.CommentRequestDTO;
import com.lsm.backend.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDTO createComment(CommentRequestDTO commentDTO, Long id, UserPrincipal userPrincipal);

    CommentDTO updateComment(CommentDTO commentDTO, Long id);

//    Optional<CommentDTO> getComment(Long id);
//
//    List<CommentDTO> getAllComment();

    void deleteComment(Long id);
}
