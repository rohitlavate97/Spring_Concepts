package com.farmcollector.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FarmException extends RuntimeException {

    private HttpStatus status;

    public FarmException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public FarmException(String message){
        super(message);

    }

}
