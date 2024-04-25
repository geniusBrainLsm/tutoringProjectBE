package com.lsm.backend.service;

import com.lsm.backend.payload.board.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
//페이지네이션 해야됌

public interface BoardService {
    BoardDTO createPost(BoardDTO boardDTO);
    BoardDTO updatePost(BoardDTO boardDTO);

    Optional<BoardDTO> getPost(Long id);
    Page<BoardDTO> getSearchPost(String boardType, String keyword, String sortBy ,Pageable pageable);
    Page<BoardDTO> getAllPost(Pageable pageable);
    void deletePost(Long id);
}