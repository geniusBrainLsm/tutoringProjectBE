package com.lsm.backend.payload.board;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Course;
import com.lsm.backend.model.Image;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String fileName;
    private String type;
    private String filePath;
    private Long boardId;
    private Long courseId;

    public static ImageDTO fromEntity(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .type(image.getType())
                .filePath(image.getFilePath())
                .boardId(image.getBoard() != null ? image.getBoard().getId() : null)
                .courseId(image.getCourse() != null ? image.getCourse().getId() : null)
                .build();
    }

    public Image toEntity() {
        Image image = Image.builder()
                .id(this.id)
                .fileName(this.fileName)
                .type(this.type)
                .filePath(this.filePath)
                .build();
        if (this.boardId != null) {
            image.setBoard(Board.builder().id(this.boardId).build());
        }
        if (this.courseId != null) {
            image.setCourse(Course.builder().id(this.courseId).build());
        }
        return image;
    }
}
