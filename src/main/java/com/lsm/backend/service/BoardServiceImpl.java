package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    @Override
    public Board createPost(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        return boardRepository.save(board);
    }

    @Override
    public Board editPost(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        return boardRepository.save(board);
    }

    @Override
    public Optional<Board> getPost(Long id) {
        return boardRepository.findById(id); // Optional 반환
    }

    @Override
    public List<Board> getAllPost() {

        return boardRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
