package com.farmcollector.exception;

import com.farmcollector.dto.ErrorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class FarmCollectorExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        return new ResponseEntity<>(new ErrorDTO(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FarmException.class)
    public ProblemDetail farmException(FarmException exception){
        return constructProblemDetail(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail runtimeException(RuntimeException exception){
        return constructProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred while processing your request. Please try again later.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail sqlIntegrityConstraintViolationException(DataIntegrityViolationException ex){
        return constructProblemDetail(HttpStatus.CONFLICT, "The farm name already exists. Please ensure they are unique in the database.");
    }

    private ProblemDetail constructProblemDetail(HttpStatus httpStatus, String message) {
        return ProblemDetail.forStatusAndDetail(httpStatus, message);
    }

}
