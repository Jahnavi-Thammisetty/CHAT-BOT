package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private String id;
    private String userMessage;
    private String aiResponse;
    private LocalDateTime createdAt;
}
