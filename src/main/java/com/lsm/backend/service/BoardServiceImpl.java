package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Image;
import com.lsm.backend.model.Tag;
import com.lsm.backend.payload.board.BoardDTO;
import com.lsm.backend.repository.BoardRepository;
import com.lsm.backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final ImageService imageService;

    @Override
    public BoardDTO createPost(BoardDTO boardDTO) {
        // 태그 가져오기, 중복 체크 포함
        List<Tag> tags = Optional.ofNullable(boardDTO.getTag()).orElseGet(Collections::emptyList)
                .stream()
                .map(tagDTO -> tagRepository.findByContents(tagDTO.getContents())
                        .orElseGet(() -> {
                            // 태그가 존재하지 않는 경우 새로 저장
                            Tag tag = Tag.builder().contents(tagDTO.getContents()).build();
                            return tagRepository.save(tag);
                        }))
                .collect(Collectors.toList());

        // Board 엔티티로 변환 후 태그 설정
        Board board = boardDTO.toEntity();
        board.setTag(tags);

        // 이미지 저장 및 설정
        List<Image> images = imageService.saveImages(boardDTO.getMultipartFileImage(), board.getId());
        board.setImage(images);

        // Board 엔티티 저장
        board = boardRepository.save(board);

        // 댓글 수 세기
        Long commentsCount = boardRepository.countCommentsById(board.getId());
        board.setCommentsCount(commentsCount);

        return BoardDTO.fromEntity(board);
    }

    @Override
    public BoardDTO updatePost(Long boardId, BoardDTO boardDTO) {
        // 기존 보드 찾기
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

        // 태그 업데이트
        List<Tag> tags = Optional.ofNullable(boardDTO.getTag()).orElseGet(Collections::emptyList)
                .stream()
                .map(tagDTO -> tagRepository.findByContents(tagDTO.getContents())
                        .orElseGet(() -> {
                            Tag tag = Tag.builder().contents(tagDTO.getContents()).build();
                            return tagRepository.save(tag);
                        }))
                .collect(Collectors.toList());
        board.setTag(tags);

        // 이미지 업데이트
        if (boardDTO.getMultipartFileImage() != null && !boardDTO.getMultipartFileImage().isEmpty()) {
            List<Image> images = imageService.saveImages(boardDTO.getMultipartFileImage(), board.getId());
            board.setImage(images);
        }

        // 다른 필드 업데이트
        if (boardDTO.getTitle() != null) {
            board.setTitle(boardDTO.getTitle());
        }
        if (boardDTO.getContents() != null) {
            board.setContents(boardDTO.getContents());
        }

        // Board 엔티티 저장
        board = boardRepository.save(board);

        // 댓글 수 업데이트
        Long commentsCount = boardRepository.countCommentsById(board.getId());
        board.setCommentsCount(commentsCount);

        return BoardDTO.fromEntity(board);
    }

    @Override
    public Optional<BoardDTO> getPost(Long id) {
        try {
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
            long currentViewCount = board.getViewCount();
            board.setViewCount(currentViewCount + 1L);
            Board updatedBoard = boardRepository.save(board); // 조회수 DB에 저장
            return Optional.of(BoardDTO.fromEntity(updatedBoard));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Page<BoardDTO> getSearchPost(String boardType, String keyword, String sortBy, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 5;
        Sort sort;
        if (sortBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "viewCount");
        } else if (sortBy.equals("like")) {
            sort = Sort.by(Sort.Direction.DESC, "likeCount");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Board> boards = boardRepository.findByBoardTypeAndTitleContaining(boardType, keyword, sortedPageable);

        return boards.map(BoardDTO::fromEntity);
    }

    @Override
    public Page<BoardDTO> getAllPost(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id")));
        return boards.map(BoardDTO::fromEntity);
    }

    @Override
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}