package com.chat.util;

import com.chat.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {
    public ResponseEntity<CommonResponseDto> prepareResponse(HttpStatus status, String message, Object responseObject) {
        var responseDTO = new CommonResponseDto();
        responseDTO.setMessage(message);
        responseDTO.setResponseObject(responseObject);
        return new ResponseEntity<>(responseDTO, status);
    }
}
