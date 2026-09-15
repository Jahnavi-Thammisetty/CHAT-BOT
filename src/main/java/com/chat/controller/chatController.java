package com.chat.controller;

import com.chat.dto.ChatDto;
import com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chat")
public class chatController {
    @Autowired
    private ChatService chatService;
    @PostMapping(value = "/")
    public ChatDto saveAndGetchat(@RequestBody ChatDto chatDto){
        return chatService.createAndGetChat(chatDto) ; 
    }
    @GetMapping(value = "/{userId}")
    public List<ChatDto> getChatDetailsForUser(@PathVariable String userId){
        return chatService.getChatDetailsByUserId(userId);
    }
}
