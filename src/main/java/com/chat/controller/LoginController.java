package com.chat.controller;

import com.chat.dto.CommonResponseDto;
import com.chat.dto.UserDtou;
import com.chat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponseDto> signUp(@RequestBody UserDtou userDtou){
        return loginService.signUp(userDtou);
    }
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserDtou userDtou){
        return loginService.login(userDtou);
    }
}
