package com.chat.service;

import com.chat.dao.impl.ChatData;
import com.chat.dto.ChatDto;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private ChatData chatData;

    private final ChatLanguageModel chatLanguageModel;

    public ChatService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    public ChatDto createAndGetChat(ChatDto chatDto) {
        chatData.saveChatData(chatDto);
        String userMessage = chatDto.getUserMessage();
        ChatRequest request = ChatRequest.builder()
                .messages(UserMessage.from(userMessage))
                .build();
        ChatResponse response = chatLanguageModel.chat(request);
        String aiResponse = response.aiMessage().text();
        chatDto.setId(UUID.randomUUID().toString());
        chatDto.setAiResponse(aiResponse);
        chatDto.setCreatedAt(LocalDateTime.now());
        chatDto.setIsAnswer(true);
        chatData.saveChatData(chatDto);
        return chatDto;
    }
    public List<ChatDto> getChatDetailsByUserId(String userId){
       return chatData.getChatDetailsForUser(userId);
    }
}