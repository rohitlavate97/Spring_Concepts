package com.publicis.sapient.weather.application.util;

public final class WeatherConstants {

    // Prevent instantiation
    private WeatherConstants() {}

    // Weather-related constants
    public static final String WEATHER = "weather";
    public static final String MAIN = "main";
    public static final String DESCRIPTION = "description";
    public static final String ICON = "icon";

    // Weather Advice messages
    public static final String SUNSCREEN_ADVICE = "Use sunscreen lotion, too sunny";
    public static final String UMBRELLA_ADVICE = "Carry umbrella, It will rain";
    public static final String WINDY_ADVICE = "It's too windy, watch out!";
    public static final String THUNDERSTORM_ADVICE = "Don't step out! A Storm is brewing!";
    public static final String CLOUDY_WEATHER_ADVICE = "Expect cloudy weather conditions.";

    // Temperature thresholds
    public static final double HIGH_TEMP_THRESHOLD = 40.0; // Celsius
    public static final double WIND_SPEED_THRESHOLD = 10.0; // mph

    //Generic
    public static final String WEATHER_SERVICE_UNAVAILABLE = "Weather API service is not available at this moment. Please try after sometime.";

}
