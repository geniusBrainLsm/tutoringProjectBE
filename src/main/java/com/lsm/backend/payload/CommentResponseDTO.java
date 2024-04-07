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
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String userName;
    private Long boardId;
    private Long parentId;
    public static CommentResponseDTO fromEntity(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userName(comment.getUser().getName())
                .boardId(comment.getBoard().getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
