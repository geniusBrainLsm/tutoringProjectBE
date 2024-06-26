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
    private final ImageServiceImpl imageService;
    @Override
    public BoardDTO createPost(BoardDTO boardDTO) {
        //태그가져오기. 중복체크포함해서
        List<Tag> tags = Optional.ofNullable(boardDTO.getTag()).orElseGet(Collections::emptyList)
                .stream()
                .map(tagDTO -> {
                    //태그 검색하고 없으면? ->
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

        board.setTag(tags);


//        List<Image> images = Optional.ofNullable(boardDTO.getImage())
//                .orElseGet(Collections::emptyList)
//                .stream()
//                .map(imageDTO -> {
//                    Image image = new Image();
//                    image.setFileName(imageDTO.getFileName());
//                    image.setFilePath(imageDTO.getFilePath());
//                    image.setType(imageDTO.getType());
//                    //image.setBoard(board); // 이미지와 게시글 연결은? 안해도될듯
//                    return image;
//                })
//                .collect(Collectors.toList());
//        board.setImage(images);
//        // Board 엔티티 저장
//
//        imageService.uploadImage(images);

        List<Image> images = imageService.uploadImage(boardDTO.getMultipartFileImage());
        board.setImage(images);

        board = boardRepository.save(board);

        Long commentsCount = boardRepository.countCommentsById(board.getId());

        board.setCommentsCount(commentsCount);
        return BoardDTO.fromEntity(board);
    }
    @Override
    public BoardDTO updatePost(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardDTO.getId()));
//        240507 태그로직 변경. 태그 업데이트시 기존 태그 삭제되는 문제 해결.

        List<Tag> existingTags = new ArrayList<>(board.getTag());
        List<Tag> newTags = Optional.ofNullable(boardDTO.getTag()).orElseGet(Collections::emptyList)
                .stream()
                .map(tagDTO -> tagRepository.findByContents(tagDTO.getContents())
                        .orElseGet(() -> {
                            Tag tag = Tag.builder()
                                    .contents(tagDTO.getContents())
                                    .build();
                            return tagRepository.save(tag);
                        }))
                .toList();

        List<Tag> tagsToRemove = existingTags.stream()
                .filter(tag -> newTags.stream().noneMatch
                        (newTag -> tag.getContents() != null && newTag.getContents() !=
                                null && tag.getContents().equals(newTag.getContents())))
                .toList();

        existingTags.removeAll(tagsToRemove);

        existingTags.addAll(newTags);

        board.setTag(existingTags);
        board.setTitle(boardDTO.getTitle());
        board.setContents(boardDTO.getContents());
        board.setWriter(boardDTO.getWriter());

        board = boardRepository.save(board);

        board.setCommentsCount(boardDTO.getCommentsCount());

        return BoardDTO.fromEntity(board);
    }


    @Override
    public Optional<BoardDTO> getPost(Long id) {
        try{
            Board board = boardRepository.findById(id)
                    .orElseThrow(()-> new Exception("없어용"));
            long currentViewCount = board.getViewCount();
            board.setViewCount(currentViewCount + 1L);
            Board updatedBoard = boardRepository.save(board); //조회수 db에저장해야됌
            return Optional.of(BoardDTO.fromEntity(updatedBoard));

        } catch (Exception e){
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
