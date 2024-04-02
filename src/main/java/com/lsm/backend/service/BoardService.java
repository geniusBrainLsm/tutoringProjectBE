package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.TagDTO;
import com.lsm.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
//페이지네이션 해야됌

public interface BoardService {
    BoardDTO createPost(BoardDTO boardDTO);
    BoardDTO updatePost(BoardDTO boardDTO);

    Optional<BoardDTO> getPost(Long id);
    List<BoardDTO> getAllPost();
    void deletePost(Long id);
}