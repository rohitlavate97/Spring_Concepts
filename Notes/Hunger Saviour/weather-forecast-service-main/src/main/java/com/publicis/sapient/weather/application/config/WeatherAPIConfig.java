package com.publicis.sapient.weather.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather")
public record WeatherAPIConfig(String apiKey, String url) {
}
