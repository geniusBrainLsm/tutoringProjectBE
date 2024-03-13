package com.lsm.backend.payload;

import lombok.Data;

@Data //POJO bean과 관련된 모든 보일러플레이트 생성
public class Coordinates {
    private Long x0;
    private Long y0;
    private Long x1;
    private Long y1;
}
