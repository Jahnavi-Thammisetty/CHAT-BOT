package com.chat.dao.impl;

import com.chat.config.MongoUtilService;
import com.chat.dto.MongoCollection;
import com.chat.dto.UserDtou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl  {
    @Autowired
    private MongoUtilService mongoUtilService;
    public UserDtou saveUserData(UserDtou userDtou) {
        return mongoUtilService.getMongoTemplate().save(userDtou, MongoCollection.user_details.name());
    }
    public UserDtou findByEmailId(String emailId){
      return  mongoUtilService.getMongoTemplate().findOne(Query.query(Criteria.where("email").is(emailId)), UserDtou.class, MongoCollection.user_details.name());
    }
}
