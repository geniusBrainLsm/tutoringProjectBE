package com.lsm.backend.payload;

import com.lsm.backend.model.Board;
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

    private Long likeCount;

    private Long viewCounter;

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
                                .contents(tagDTO.getContents())
                                .build())
                        .collect(Collectors.toList()))
                        .build();
    }

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .boardType(board.getBoardType())
                .title(board.getTitle())
                .writer(board.getWriter())
                .contents(board.getContents())
                .likeCount(board.getLikeCount())
                .viewCounter(board.getViewCounter())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .tag(board.getTag().stream()
                        .map(tag -> TagDTO.builder()
                                .contents(tag.getContents())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
