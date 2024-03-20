package com.lsm.backend.payload;

import com.lsm.backend.model.Board;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {
    private String boardType;

    private String title;

    private String writer;

    private Long likeCount;

    private Long viewCounter;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public Board toEntity() {
        Board board = new Board();
        board.setBoardType(this.boardType);
        board.setTitle(this.title);
        board.setWriter(this.writer);
        board.setLikeCount(this.likeCount);
        board.setViewCounter(this.viewCounter);
        board.setCreatedAt(this.createdAt);
        board.setModifiedAt(this.modifiedAt);

        return board;
    }
    public static BoardDTO fromEntity(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardType(board.getBoardType());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setWriter(board.getWriter());
        boardDTO.setLikeCount(board.getLikeCount());
        boardDTO.setViewCounter(board.getViewCounter());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setModifiedAt(board.getModifiedAt());
        return boardDTO;
    }
}
