package com.lsm.backend.payload.board;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Tag;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private Long viewCount;
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
                .viewCount(viewCount)

                .tag(Optional.ofNullable(tag).orElseGet(Collections::emptyList)
                        .stream()
                        .map(tagDTO -> Tag.builder()
                                .id(tagDTO.getId())
                                .contents(tagDTO.getContents())
                                .build())
                        .collect(Collectors.toList()))
                .commentsCount(commentsCount)
                .build();
    }

    public static BoardDTO fromEntity(Board board) {
        Long commentsCount;
        if(board.getComment() == null){
            commentsCount = 0L;
        }
        else{
            commentsCount = (long) board.getComment().size();
        }

        //이건 댓글수는 Entity에 없으니까 이렇게 불러와서 빌드함
        List<TagDTO> tagDTOs = Optional.ofNullable(board.getTag())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(tag -> TagDTO.builder()
                        .id(tag.getId())
                        .contents(tag.getContents())
                        .build())
                .collect(Collectors.toList());

        List<CommentResponseDTO> commentResponseDTOs = Optional.ofNullable(board.getComment())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(comment -> CommentResponseDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .boardId(comment.getBoard().getId())
                        .parentId(comment.getParent().getId() != null ? comment.getParent().getId() : null)
                        .userName(comment.getUser().getName())
                        .build())
                .collect(Collectors.toList());

        return BoardDTO.builder()
                .id(board.getId())
                .boardType(board.getBoardType())
                .title(board.getTitle())
                .writer(board.getWriter())
                .contents(board.getContents())
                .commentsCount(commentsCount)
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .tag(tagDTOs)

                .comment(commentResponseDTOs)
                .build();
    }
}