package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


public interface BoardService {

    Board createPost(BoardDTO boardDTO);

    Board editPost(BoardDTO boardDTO);

    Optional<Board> getPost(Long id);

    List<Board> getAllPost();

    void deletePost(Long id);



}
