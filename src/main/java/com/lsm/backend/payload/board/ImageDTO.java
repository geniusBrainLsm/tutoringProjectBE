package com.lsm.backend.payload.board;

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
}
