package com.hunger.saviour.portal.handlers;


import com.hunger.saviour.portal.dtos.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class RestaurantAPIHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.info("Entered into RestaurantAPIHandler class with exception : "+ exception.getMessage());
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .error_messages(exception.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList())
                .statusCodeDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
