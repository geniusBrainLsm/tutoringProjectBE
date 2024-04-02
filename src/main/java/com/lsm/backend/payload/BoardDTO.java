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
@RequiredArgsConstructor
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
        Board board = Board.builder()
                .title(title)
                .writer(writer)
                .boardType(boardType)
                .contents(contents)
                .likeCount(likeCount)
                .viewCounter(viewCounter)
                .build();

        List<Tag> tagEntities = new ArrayList<>();
        for(TagDTO tagDTO : tag){
            Tag tag = Tag.builder()
                    .contents(tagDTO.getContents())
                    .build();
            tagEntities.add(tag);
        }

        board.setTag(tagEntities);
        return board;
    }

    public static BoardDTO fromEntity(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setBoardType(board.getBoardType());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setWriter(board.getWriter());
        boardDTO.setContents(board.getContents());
        boardDTO.setLikeCount(board.getLikeCount());
        boardDTO.setViewCounter(board.getViewCounter());
        boardDTO.setCreatedAt(board.getCreatedAt());
        boardDTO.setModifiedAt(board.getModifiedAt());

        List<TagDTO> tagDTOS = board.getTag().stream()
                .map(tag->TagDTO.builder()
                        .contents(tag.getContents())
                        .build())
                .collect(Collectors.toList());
        boardDTO.setTag(tagDTOS);

        return boardDTO;
    }
}
