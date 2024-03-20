package com.lsm.backend.service;

import com.lsm.backend.model.Board;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    @Override
    public BoardDTO createPost(BoardDTO boardDTO) {
        Board board = boardRepository.save(boardDTO.toEntity());
        return BoardDTO.fromEntity(board);
    }

    @Override
    public BoardDTO editPost(BoardDTO boardDTO) {
        Board board = boardRepository.save(boardDTO.toEntity());
        return BoardDTO.fromEntity(board);
    }

    @Override
    public Optional<BoardDTO> getPost(Long id) {
        try{
            Board board = boardRepository.findById(id)
                    .orElseThrow(()-> new Exception("없어용"));

            return Optional.of(BoardDTO.fromEntity(board));

        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public List<BoardDTO> getAllPost() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        List<Board> boards = boardRepository.findAll();

        for(Board board : boards){
            BoardDTO boardDto = BoardDTO.fromEntity(board);
            boardDTOList.add(boardDto);
        }
        return boardDTOList;
    }

    @Override
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
