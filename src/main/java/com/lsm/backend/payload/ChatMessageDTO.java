package com.lsm.backend.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter

public class ChatMessageDTO {
    private String content;
    private String sender;
}
