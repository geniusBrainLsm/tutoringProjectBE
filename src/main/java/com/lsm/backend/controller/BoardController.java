package com.lsm.backend.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardServiceImpl boardService;
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody BoardDTO boardDTO){
        BoardDTO createdDTO = boardService.createPost(boardDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        boardService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Optional<BoardDTO> board = boardService.getPost(id);

        if(board.isPresent()) {
            return new ResponseEntity<>(board.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<BoardDTO>> getSearchPost(@PathVariable String boardType ,@RequestParam String keyword,
                                                        @RequestParam String sortBy,
                                                        @PageableDefault(page = 1) Pageable pageable){
        Sort sort;
        if (sortBy.equals("view")) {
            sort = Sort.by(Sort.Direction.DESC, "viewCount");
        } else if(sortBy.equals("like")) {
            sort = Sort.by(Sort.Direction.DESC, "likeCount");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        List<BoardDTO> searchResult = boardService.getSearchPost(boardType, keyword, sortedPageable);

        return ResponseEntity.ok(searchResult);
    }
    @GetMapping
    public ResponseEntity<Page<BoardDTO>> getAllPost(@PageableDefault(page = 1) Pageable pageable){
        Page<BoardDTO> posts = boardService.getAllPost(pageable);
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), posts.getTotalPages());
        return ResponseEntity.ok(posts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody BoardDTO boardDTO){
        BoardDTO createdDTO = boardService.updatePost(boardDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }
}
