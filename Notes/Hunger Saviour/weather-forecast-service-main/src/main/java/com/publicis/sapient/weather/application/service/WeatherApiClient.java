package com.publicis.sapient.weather.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.publicis.sapient.weather.application.config.WeatherAPIConfig;
import com.publicis.sapient.weather.application.exception.WeatherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.publicis.sapient.weather.application.util.WeatherConstants.WEATHER_SERVICE_UNAVAILABLE;

@Component
public class WeatherApiClient {

    private final WebClient webClient;
    private final WeatherAPIConfig weatherAPIConfig;

    public WeatherApiClient(WebClient.Builder webClientBuilder,
                            WeatherAPIConfig weatherAPIConfig) {
        this.webClient = webClientBuilder.baseUrl(weatherAPIConfig.url()).build();
        this.weatherAPIConfig = weatherAPIConfig;
    }

    public Mono<JsonNode> fetchWeatherData(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("q", city)
                        .queryParam("appid", weatherAPIConfig.apiKey())
                        .queryParam("cnt", 24)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // Handle 4xx errors here
                                    return Mono.error(new WeatherException(HttpStatus.UNAUTHORIZED,
                                            "API Key is invalid",
                                            errorBody));
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    return Mono.error(new WeatherException(HttpStatus.INTERNAL_SERVER_ERROR,
                                            WEATHER_SERVICE_UNAVAILABLE,
                                            errorBody));
                                })
                )
                .bodyToMono(JsonNode.class);
    }
}
