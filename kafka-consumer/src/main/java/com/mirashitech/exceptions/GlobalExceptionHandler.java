package com.mirashitech.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // this will catch listener exceptions
public class GlobalExceptionHandler {
    public ResponseEntity<String> handleException(Exception ex){
        log.debug("---------Exception------------: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred: " + ex.getMessage());
    }
}
