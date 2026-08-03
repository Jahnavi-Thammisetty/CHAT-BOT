package com.chat.service;

import com.chat.dto.ChatDto;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.UUID;

@Service
public class ChatService {

    private final ChatLanguageModel chatLanguageModel;

    public ChatService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    public ChatDto getChat(ChatDto chatDto) {

        String userMessage = chatDto.getUserMessage();
        ChatRequest request = ChatRequest.builder()
                .messages(UserMessage.from(userMessage))
                .build();
        ChatResponse response = chatLanguageModel.chat(request);

        String aiResponse = response.aiMessage().text();

        chatDto.setId(UUID.randomUUID().toString());
        chatDto.setAiResponse(aiResponse);
        chatDto.setCreatedAt(LocalDateTime.now());

        return chatDto;
    }
}