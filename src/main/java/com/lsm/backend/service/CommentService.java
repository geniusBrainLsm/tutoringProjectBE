package com.lsm.backend.service;

import com.lsm.backend.payload.board.CommentDTO;
import com.lsm.backend.payload.board.CommentRequestDTO;
import com.lsm.backend.payload.board.CommentResponseDTO;
import com.lsm.backend.security.UserPrincipal;

public interface CommentService {
    CommentResponseDTO createComment(CommentRequestDTO commentDTO, UserPrincipal userPrincipal, Long id);

    CommentDTO updateComment(CommentDTO commentDTO, Long id, UserPrincipal userPrincipal);

//    Optional<CommentDTO> getComment(Long id);
//
//    List<CommentDTO> getAllComment();

    void deleteComment(Long id, UserPrincipal userPrincipal);
}
