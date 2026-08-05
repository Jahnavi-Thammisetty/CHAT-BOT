package com.chat.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoUtilService {
    public MongoTemplate getMongoTemplate(){
        String connectionUrl = "mongodb://localhost:27017";
        String databaseName = "ai_chat_db";

        MongoClient mongoClient = createMongoClient(connectionUrl);

        return new MongoTemplate(mongoClient, databaseName);
    }
    private MongoClient createMongoClient(String connectionUrl) {

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionUrl))
                .build();

        return MongoClients.create(settings);
    }
}
