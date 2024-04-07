package com.lsm.backend.payload;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.User;
import com.lsm.backend.security.UserPrincipal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentDTO {

    private Long id;
    private Board board;
    private String content;
    private Comment parent;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String userName;
    private Long boardId;
    private Long parentId;

    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .content(content)
                .user(user)
                .board(board)
                .parent(parent)
                .build();
    }
//static인이유: 객체생성없이 그냥쓰려고
    public static CommentDTO fromEntity(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(comment.getUser())
                .userName(comment.getUser().getName())
                .board(comment.getBoard())
                .boardId(comment.getBoard().getId())
                .parent(comment.getParent())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
