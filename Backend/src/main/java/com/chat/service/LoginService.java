package com.chat.service;

import com.chat.dao.impl.UserDaoImpl;
import com.chat.dto.CommonResponseDto;
import com.chat.dto.UserDtou;
import com.chat.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private UserDaoImpl userDao;
    @Autowired
    private ResponseUtils responseUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String FAILURE ="Failure";
    private static final String USER_PREFIX ="USR";
    private final JWTService jwtService;
    public LoginService(
            UserDaoImpl userDao,
            JWTService jwtService,
            PasswordEncoder passwordEncoder) {

        this.userDao = userDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public ResponseEntity<CommonResponseDto> signUp(UserDtou userDtou){
        UserDtou existingData = userDao.findByEmailId(userDtou.getEmail());
        if(Objects.nonNull(existingData)){
           return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"Given email already exist so please continue on login ",FAILURE);
        }
        userDtou.setId(USER_PREFIX+ UUID.randomUUID());
        String encodedPassword = passwordEncoder.encode(userDtou.getPassword());
        userDtou.setPassword(encodedPassword);
        UserDtou userDtou1 =userDao.saveUserData(userDtou);
        return responseUtil.prepareResponse(HttpStatus.OK,"Successfully sign up is done please continue to login",userDtou1);
    }
    public ResponseEntity<CommonResponseDto> login(UserDtou userDtou){
        UserDtou existingData = userDao.findByEmailId(userDtou.getEmail());
        if(Objects.isNull(existingData)){
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"No User Found...",FAILURE);
        }
        Boolean isValid = userDtou.getEmail().equals(existingData.getEmail()) && passwordEncoder.matches(userDtou.getPassword(),existingData.getPassword());
        if(isValid){
            String accessToken = jwtService.generateToken(existingData.getEmail());
            String refreshToken = jwtService.generateRefreshToken(existingData.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("id", existingData.getId());
            response.put("email", existingData.getEmail());
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return responseUtil.prepareResponse(HttpStatus.OK,
                    "Login success...",
                    response
            );        }else{
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"Invalid credentials....",FAILURE);
        }
    }
    public ResponseEntity<?> refresh(String authHeader) {
        String refreshToken = authHeader.substring(7);
        String tokenType = jwtService.extractTokenType(refreshToken);
        if (!"REFRESH".equals(tokenType)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token required");
        }
        String username = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        ));
    }
    public ResponseEntity<CommonResponseDto> logOut(UserDtou userDtou){
        UserDtou existingData = userDao.findByEmailId(userDtou.getEmail());
        if(Objects.isNull(existingData)){
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"No User Found...",FAILURE);
        }
        Boolean isValid = userDtou.getEmail().equals(existingData.getEmail()) && passwordEncoder.matches(userDtou.getPassword(),existingData.getPassword());
        if(isValid){
            return responseUtil.prepareResponse(HttpStatus.OK,"Login success...",existingData);
        }else{
            return responseUtil.prepareResponse(HttpStatus.BAD_REQUEST,"Invalid credentials....",FAILURE);
        }
    }
}
