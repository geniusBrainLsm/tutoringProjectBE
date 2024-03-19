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

}
