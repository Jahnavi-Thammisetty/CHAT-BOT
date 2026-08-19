package com.chat.dao.impl;

import com.chat.config.MongoUtilService;
import com.chat.dto.ChatDto;
import com.chat.dto.MongoCollection;
import com.chat.dto.UserDtou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ChatData {
    @Autowired
    private MongoUtilService mongoUtilService;
    public ChatDto saveChatData(ChatDto chatData) {
        return mongoUtilService.getMongoTemplate().save(chatData,MongoCollection.chat_documents.getCollectionName(chatData.getUserId()));
    }
    public List<ChatDto> getChatDetailsForUser(String userId){
        return mongoUtilService.getMongoTemplate().find(Query.query(Criteria.where(userId)),ChatDto.class, MongoCollection.chat_documents.getCollectionName(userId));
    }
}
