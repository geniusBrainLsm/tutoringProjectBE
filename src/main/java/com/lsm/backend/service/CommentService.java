package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);

    CommentDTO updateComment(CommentDTO commentDTO);

    Optional<CommentDTO> getComment(Long id);

    List<CommentDTO> getAllComment();

    void deleteComment(Long id);
}
