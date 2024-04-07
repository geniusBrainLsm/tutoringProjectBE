package com.lsm.backend.payload;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.Tag;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

    private Long id;

    private String boardType;

    private String title;

    private String writer;

    private String contents;

    private List<TagDTO> tag;

    private List<CommentResponseDTO> comment;

    private Long likeCount;

    private Long viewCounter;
    @Builder.Default
    private Long commentsCount = 0L;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    public Board toEntity() {

        return Board.builder()
                .title(title)
                .writer(writer)
                .boardType(boardType)
                .contents(contents)
                .likeCount(likeCount)
                .viewCounter(viewCounter)

                .tag(tag.stream()
                        .map(tagDTO -> Tag.builder()
                                .id(tagDTO.getId())
                                .contents(tagDTO.getContents())
                                .build())
                        .collect(Collectors.toList()))
                .commentsCount(commentsCount)
                .build();
    }

    public static BoardDTO fromEntity(Board board) {
        Long commentsCount = (long) board.getComment().size();
        //이건 댓글수는 Entity에 없으니까 이렇게 불러와서 빌드함

        return BoardDTO.builder()
                .id(board.getId())
                .boardType(board.getBoardType())
                .title(board.getTitle())
                .writer(board.getWriter())
                .contents(board.getContents())
                .commentsCount(commentsCount)
                .likeCount(board.getLikeCount())
                .viewCounter(board.getViewCounter())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .tag(board.getTag().stream()
                        .map(tag -> TagDTO.builder()
                                .id(tag.getId())
                                .contents(tag.getContents())
                                .build())
                        .collect(Collectors.toList()))

                .comment(board.getComment().stream()
                        .map(comment -> CommentResponseDTO.builder()
                                .id(comment.getId())
                                .content(comment.getContent())
                                .boardId(comment.getBoard().getId())
                                .parentId(comment.getParent().getId() != null ? comment.getParent().getId() : null)
                                .userName(comment.getUser().getName())
                                .build())
                                .collect(Collectors.toList())
                        )
                .build();
    }
}
