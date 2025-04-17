package com.publicis.sapient.weather.application.dto;

import java.util.List;

public class WeatherForecast {
    private String date;
    private double highTemp;
    private double lowTemp;
    private List<String> weatherAdvice;
    private String icon;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public List<String> getWeatherAdvice() {
        return weatherAdvice;
    }

    public void setWeatherAdvice(List<String> weatherAdvice) {
        this.weatherAdvice = weatherAdvice;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
