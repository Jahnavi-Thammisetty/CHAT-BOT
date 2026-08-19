package com.chat.service;

import com.chat.dao.impl.UserDaoImpl;
import com.chat.dto.CommonResponseDto;
import com.chat.dto.UserDtou;
import com.chat.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private ResponseUtils responseUtil;
    private static final String FAILURE ="Failure";
    private static final String SUCCESS ="Success";
    private static final String USER_PREFIX ="USR";
    
    public ResponseEntity<CommonResponseDto> signUp(UserDtou userDtou){
        UserDtou existingData = userDao.findByEmailId(userDtou.getEmail());
        if(Objects.nonNull(existingData)){
           return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"Given email already exist so please continue on login ",FAILURE);
        }
        userDtou.setId(USER_PREFIX+ UUID.randomUUID());
        UserDtou userDtou1 =userDao.saveUserData(userDtou);
        return responseUtil.prepareResponse(HttpStatus.OK,"Successfully sign up is done please continue to login",userDtou1);
    }
    public ResponseEntity<CommonResponseDto> login(UserDtou userDtou){
        UserDtou existingData = userDao.findByEmailId(userDtou.getEmail());
        if(Objects.isNull(existingData)){
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"No User Found...",FAILURE);
        }
        Boolean isValid = userDtou.getEmail().equals(existingData.getEmail()) && userDtou.getPassword().equals(existingData.getPassword());
        if(isValid){
            return responseUtil.prepareResponse(HttpStatus.OK,"Login success...",existingData);
        }else{
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"Invalid credentials....",FAILURE);
        }
    }
}
