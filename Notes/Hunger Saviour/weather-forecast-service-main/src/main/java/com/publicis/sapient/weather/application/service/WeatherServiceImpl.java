package com.publicis.sapient.weather.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.publicis.sapient.weather.application.dto.WeatherForecast;
import com.publicis.sapient.weather.application.util.WeatherDataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.publicis.sapient.weather.application.util.WeatherConstants.*;

@Service
@Slf4j
public class WeatherServiceImpl implements IWeatherService {
    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public Mono<List<WeatherForecast>> getWeatherForecast(String city) {
        return weatherApiClient.fetchWeatherData(city)
                .doOnNext(WeatherDataCache::cacheWeatherData)
                .map(this::parseWeatherData);
    }

    private List<WeatherForecast> parseWeatherData(JsonNode node) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        for (JsonNode day : node.path("list")) {
            List<String> adviceList = new ArrayList<>();
            WeatherForecast forecast = new WeatherForecast();
            forecast.setDate(day.path("dt_txt").asText());
            forecast.setHighTemp(day.path("main").path("temp_max").asDouble() - 273.15); // Convert from Kelvin to Celsius
            forecast.setLowTemp(day.path("main").path("temp_min").asDouble() - 273.15);
            forecast.setWeatherAdvice(determineAdvice(day, adviceList));
            String iconCode = day.path("weather").get(0).path("icon").asText();
            forecast.setIcon("https://openweathermap.org/img/wn/" + iconCode + "@2x.png");
            forecasts.add(forecast);
        }
        return forecasts;
    }

    private List<String> determineAdvice(JsonNode day, List<String> adviceList) {
        double highTemp = day.path(MAIN).path("temp_max").asDouble() - 273.15; // Convert from Kelvin to Celsius
        double windSpeed = day.path("wind").path("speed").asDouble();
        int weatherId = day.path(WEATHER).get(0).path("id").asInt();

        if (highTemp > 40) {
            adviceList.add(SUNSCREEN_ADVICE);
        }
        if (weatherId >= 500 && weatherId < 600) {
            adviceList.add(UMBRELLA_ADVICE);
        }
        if (windSpeed > 10) {
            adviceList.add(WINDY_ADVICE);
        }
        if (weatherId >= 200 && weatherId < 300) {
            adviceList.add(THUNDERSTORM_ADVICE);
        }
        if (weatherId >= 800 && weatherId <= 804) {
            adviceList.add(CLOUDY_WEATHER_ADVICE);
        }
        return adviceList;
    }
}