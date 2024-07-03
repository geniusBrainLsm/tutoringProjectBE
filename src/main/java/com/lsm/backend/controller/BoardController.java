package com.lsm.backend.controller;

import com.lsm.backend.payload.board.BoardDTO;
import com.lsm.backend.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardServiceImpl boardService;
//멀티파트파일이미지는 json 응답에서 필요가 없으니까 이름을 이렇게 지었다...
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPost(@RequestPart("boardDTO") BoardDTO boardDTO,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        System.out.println("Received boardDTO: " + boardDTO);
        System.out.println("Received files: " + files);
        boardDTO.setMultipartFileImage(files);
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

    @GetMapping("/search/{boardType}")
    public ResponseEntity<Page<BoardDTO>> getSearchPost(@PathVariable String boardType,
                                                        @RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) String sortBy,
                                                        @PageableDefault(page = 1) Pageable pageable) {

        Page<BoardDTO> searchResult = boardService.getSearchPost(boardType, keyword, sortBy, pageable);

        return ResponseEntity.ok(searchResult);
    }

    @GetMapping
    public ResponseEntity<Page<BoardDTO>> getAllPost(@PageableDefault(page = 1) Pageable pageable) {
        Page<BoardDTO> posts = boardService.getAllPost(pageable);
        return ResponseEntity.ok(posts);
    }

    @PutMapping(value = "/{boardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updatePost(@PathVariable Long boardId,
                                        @RequestPart("boardDTO") BoardDTO boardDTO,
                                        @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        System.out.println("Updating boardId: " + boardId);
        System.out.println("Received boardDTO: " + boardDTO);
        System.out.println("Received files: " + files);

        boardDTO.setMultipartFileImage(files);

        BoardDTO updatedDTO = boardService.updatePost(boardId, boardDTO);

        return ResponseEntity.ok(updatedDTO);
    }

}
