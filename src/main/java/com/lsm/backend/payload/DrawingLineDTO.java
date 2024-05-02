package com.lsm.backend.payload;

import lombok.Data;

@Data
public class DrawingLineDTO {
    private Long x0;
    private Long y0;
    private Long x1;
    private Long y1;
    private String color;
    private Long lineWidth;
    private String username;
}

