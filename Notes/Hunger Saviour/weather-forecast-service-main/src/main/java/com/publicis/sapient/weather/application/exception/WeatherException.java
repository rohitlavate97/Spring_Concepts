package com.publicis.sapient.weather.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class WeatherException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String developerMessage;
    private final String exceptionMessage;
}
