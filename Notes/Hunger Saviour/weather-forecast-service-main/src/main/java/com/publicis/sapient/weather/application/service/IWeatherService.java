package com.publicis.sapient.weather.application.service;

import com.publicis.sapient.weather.application.dto.WeatherForecast;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IWeatherService {
    Mono<List<WeatherForecast>> getWeatherForecast(String city);
}
