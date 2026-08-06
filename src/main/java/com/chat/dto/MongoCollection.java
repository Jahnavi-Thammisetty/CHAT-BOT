package com.chat.dto;

public enum MongoCollection {
    user_details;
    
    public String getCollectionName(String userId) {
        return userId + "_" + this.name();
    }
}
