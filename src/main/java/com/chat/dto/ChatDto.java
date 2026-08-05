package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "chat-document")
public class ChatDto{
    private String id;
    private String userMessage;
    private String aiResponse;
    private LocalDateTime createdAt;
    private Boolean isQuestion;
    private Boolean isAnswer;
}
