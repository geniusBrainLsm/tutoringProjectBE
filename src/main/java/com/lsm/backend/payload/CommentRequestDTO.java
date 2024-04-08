package com.lsm.backend.payload;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CommentRequestDTO {
    private Long id;
    private Board board;
    private String content;
    private Comment parent;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .content(content)
                .user(user)
                .board(board)
                .parent(parent)
                .build();
    }
}
