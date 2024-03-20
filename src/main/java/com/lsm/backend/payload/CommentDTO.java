package com.lsm.backend.payload;

import com.lsm.backend.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {

    private Long id;
    private Long boardId;
    private String content;
    private Long userId;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Comment toEntity(){
        Comment comment = new Comment();
        comment.setId(this.id);
        comment.setContent(this.content);
        comment.setCreatedAt(this.createdAt);
        comment.setModifiedAt(this.modifiedAt);
        return comment;
    }
//static인이유: 객체생성없이 그냥쓰려고
    public static CommentDTO fromEntity(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBoardId(comment.getBoard().getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getId());
        //이거는 없을수도있고 있을수도있으니까
        dto.setParentId(comment.getParent() != null ?
                comment.getParent().getId() : null);

        dto.setCreatedAt(comment.getCreatedAt());
        dto.setModifiedAt(comment.getModifiedAt());
        return dto;
    }
}
