package com.chat.controller;

import com.chat.dto.ChatDto;
import com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class chatController {
    @Autowired
    private ChatService chatService;
    @PostMapping(value = "/")
    public ChatDto chat(@RequestBody ChatDto chatDto){
      
        return chatService.getChat(chatDto) ; 
    }
}
