package com.lsm.backend.payload;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
@Getter
@Setter

public class DrawMessageDTO {
    private String sender;
    private int x0;
    private int x1;
    private int y0;
    private int y1;
    private Color color; // 그리기 색상
    private int thickness; // 그리기 두께
}
