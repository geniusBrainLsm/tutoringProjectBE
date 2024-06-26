package com.lsm.backend.payload.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Image;
import com.lsm.backend.model.Tag;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JsonIgnore
    private List<MultipartFile> MultipartFileImage;
    private List<CommentResponseDTO> comment;
    private List<ImageDTO> image;
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
                .image(Optional.ofNullable(image).orElseGet(Collections::emptyList)
                        .stream()
                        .map(imageDTO -> Image.builder()
                                .id(imageDTO.getId())
                                .fileName(imageDTO.getFileName())
                                .type(imageDTO.getType())
                                .filePath(imageDTO.getFilePath())
                                .build())
                        .collect(Collectors.toList()))
                .commentsCount(commentsCount)
                .build();
    }

    public static BoardDTO fromEntity(Board board) {
        Long commentsCount = Optional.ofNullable(board.getComment())
                .map(List::size)
                .map(Long::valueOf)
                .orElse(0L);

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
                        .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                        .userName(comment.getUser().getName())
                        .build())
                .collect(Collectors.toList());

        List<ImageDTO> imageDTOs = Optional.ofNullable(board.getImage())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(image -> ImageDTO.builder()
                        .id(image.getId())
                        .fileName(image.getFileName())
                        .type(image.getType())
                        .filePath(image.getFilePath())
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
                .image(imageDTOs)
                .build();
    }
}

