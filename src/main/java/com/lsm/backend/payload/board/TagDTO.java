package com.lsm.backend.payload.board;

import com.lsm.backend.model.Tag;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;

    private String contents;

    public Tag toEntity() {
        Tag entity = new Tag();
        entity.setId(this.id);
        entity.setContents(this.contents);
        return entity;
    }

    public static TagDTO fromEntity(Tag entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setContents(entity.getContents());
        return dto;
    }
}
