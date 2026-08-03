package com.chat.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Base64;

@Configuration("aiChatModelConfig")
public class ChatGroqBean {
   
    @Bean("aiChat")    
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .baseUrl("https://api.groq.com/openai/v1")
//                .apiKey()
                .modelName("llama-3.3-70b-versatile")
                .build();
    }
}
