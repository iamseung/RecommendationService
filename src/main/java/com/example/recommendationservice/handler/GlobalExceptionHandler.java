package com.example.recommendationservice.handler;

import com.example.recommendationservice.domain.dto.response.ErrorResponseDto;
import com.example.recommendationservice.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode().getStatus().value(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }
}
