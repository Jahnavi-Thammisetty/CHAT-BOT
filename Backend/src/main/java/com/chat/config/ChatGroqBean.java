package com.chat.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.context.annotation.Configuration;


@Configuration("aiChatModelConfig")
public class ChatGroqBean {
   
    @Bean("aiChat")    
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .baseUrl("https://api.groq.com/openai/v1")
//                .apiKey()
                .modelName("openai/gpt-oss-20b")
                .build();
    }
}
