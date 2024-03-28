package com.lsm.backend.payload;

import com.lsm.backend.model.CourseUpdateHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateHistoryDTO {
    private String contents;
    public CourseUpdateHistory toEntity(){
        CourseUpdateHistory history = new CourseUpdateHistory();
        history.setContents(this.contents);
        return history;
    }

    public static CourseUpdateHistoryDTO fromEntity(CourseUpdateHistory history){
        CourseUpdateHistoryDTO dto = new CourseUpdateHistoryDTO();
        dto.setContents(history.getContents());
        return dto;
    }
}
