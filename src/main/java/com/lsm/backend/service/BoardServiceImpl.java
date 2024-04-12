package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.Tag;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.payload.TagDTO;
import com.lsm.backend.repository.BoardRepository;
import com.lsm.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        //태그가져오기. 중복체크포함해서
        List<Tag> tags = Optional.ofNullable(boardDTO.getTag()).orElseGet(Collections::emptyList)
                .stream()
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
        //댓글수세기

        Board board = boardDTO.toEntity();

        board.setTag(tags); // 저장된 또는 기존의 Tag 엔티티 설정


        // Board 엔티티 저장
        board = boardRepository.save(board);

        Long commentsCount = boardRepository.countCommentsById(board.getId());

        board.setCommentsCount(commentsCount);
        return BoardDTO.fromEntity(board);
    }
    @Override
    public BoardDTO updatePost(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardDTO.getId()));

        // 태그 가져오기, 중복 체크 포함
        List<Tag> tags = boardDTO.getTag().stream()
                .map(tagDTO -> {
                    return tagRepository.findByContents(tagDTO.getContents())
                            .orElseGet(() -> {
                                Tag tag = Tag.builder()
                                        .contents(tagDTO.getContents())
                                        .build();
                                return tagRepository.save(tag);
                            });
                })
                .collect(Collectors.toList());

        board.setTag(tags);
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());

        // Board 엔티티 저장
        board = boardRepository.save(board);

        // 댓글 수 업데이트
        Long commentsCount = boardRepository.countCommentsById(board.getId());
        board.setCommentsCount(commentsCount);
        return BoardDTO.fromEntity(board);
    }


    @Override
    public Optional<BoardDTO> getPost(Long id) {
        try{
            Board board = boardRepository.findById(id)
                    .orElseThrow(()-> new Exception("없어용"));
            board.setViewCounter(board.getViewCounter()+1);
            return Optional.of(BoardDTO.fromEntity(board));

        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Page<BoardDTO> getAllPost(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 7;

        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDTO> boardDTOs = boards.map(board -> BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(board.getWriter())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build());

        return boardDTOs;
    }

    @Override
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
