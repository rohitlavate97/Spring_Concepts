package com.publicis.sapient.weather.application.api;

import com.publicis.sapient.weather.application.dto.WeatherForecast;
import com.publicis.sapient.weather.application.service.IWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("v1/weather")
@CrossOrigin("http://localhost:5173")
@Slf4j
@Tag(name = "Weather Service API", description = "Microservice to fetch and display the next 3 days' high and low temperatures for any city. Includes safety messages based on weather conditions such as rain, extreme heat, high winds, and thunderstorms. Users can input different cities and view results via a web interface or Postman. Supports offline mode and ensures production readiness with security measures for sensitive information.")
public class WeatherController {

    private final IWeatherService weatherService;

    public WeatherController(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @Operation(summary = "Get Operation on Weather API for next 3 days", description = "Used to get Low & Maximum Temperature in Celsius , Safety Message for weather condition with time intervals for Given City for next 3 days including today's weather forecast.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of weather data"),
            @ApiResponse(responseCode = "401", description = "API key is invalid"),
            @ApiResponse(responseCode = "404", description = "City not found - No weather data available for the specified city"),
            @ApiResponse(responseCode = "500", description = "Internal server error - An unexpected error occurred while processing the request")
    })
    public Mono<List<WeatherForecast>> getWeather(@PathVariable String city) {
        log.info("Received request for weather data for city: {}", city);
        return weatherService.getWeatherForecast(city)
                .doOnSuccess(forecasts -> log.info("Successfully retrieved weather data for city: {}", city))
                .doOnError(e -> log.error("Error retrieving weather data for city: {}. Error: {}", city, e.getMessage()));
    }
}