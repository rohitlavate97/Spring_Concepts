package com.publicis.sapient.weather.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.publicis.sapient.weather.application.util.WeatherConstants.WEATHER_SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler(WeatherException.class)
    public ProblemDetail weatherException(WeatherException weatherException){
        return constructProblemDetail(weatherException.getHttpStatus(), weatherException.getDeveloperMessage(), weatherException.getExceptionMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail weatherException(RuntimeException exception){
        return constructProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, WEATHER_SERVICE_UNAVAILABLE,exception.getMessage());
    }

    private ProblemDetail constructProblemDetail(HttpStatus httpStatus, String... messages) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, messages[0]);
        if(messages.length > 1){
            problemDetail.setTitle(messages[1]);
        }
        return problemDetail;
    }


}
