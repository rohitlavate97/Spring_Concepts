package com.publicis.sapient.weather.application.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class WeatherDataCache {
    private static JsonNode cachedWeatherData;

    public static void cacheWeatherData(JsonNode weatherData) {
        cachedWeatherData = weatherData;
    }

    public static Optional<JsonNode> getCachedWeatherData() {
        return Optional.ofNullable(cachedWeatherData);
    }
}
