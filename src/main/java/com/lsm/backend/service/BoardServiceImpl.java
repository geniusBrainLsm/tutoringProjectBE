package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Tag;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.TagDTO;
import com.lsm.backend.repository.BoardRepository;
import com.lsm.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    @Override
    public BoardDTO createPost(BoardDTO boardDTO) {
        List<Tag> tags = boardDTO.getTag().stream()
                .map(tagDTO -> {
                    // 데이터베이스에서 태그 검색
                    return tagRepository.findByContents(tagDTO.getContents())
                            .orElseGet(() -> {
                                // 태그가 존재하지 않는 경우 새로 저장
                                Tag tag = Tag.builder()
                                        .contents(tagDTO.getContents())
                                        .build();
                                return tagRepository.save(tag);
                            });
                })
                .collect(Collectors.toList());
        Board board = boardDTO.toEntity();

        board.setTag(tags); // 저장된 또는 기존의 Tag 엔티티 설정

        // Board 엔티티 저장
        board = boardRepository.save(board);
        return BoardDTO.fromEntity(board);
    }
    @Override
    public BoardDTO updatePost(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardDTO.getId()));
        board.setTag(boardDTO.toEntity().getTag());
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());

        board = boardRepository.save(board);

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
