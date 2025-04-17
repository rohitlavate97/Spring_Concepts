import React from "react";
import { WeatherForecast } from "../types/weather";
import { FaTemperatureHigh, FaTemperatureLow } from "react-icons/fa";
import "bootstrap/dist/css/bootstrap.min.css";
import "./WeatherCard.css";

interface WeatherCardProps {
  forecast: WeatherForecast;
}

const WeatherCard: React.FC<WeatherCardProps> = ({ forecast }) => {
  // Create a Date object from the forecast.date
  const date = new Date(forecast.date);

  // Format the date to "Day Month Time HH:MM"
  const options: Intl.DateTimeFormatOptions = {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  };

  const formattedDate =
    date.toLocaleDateString("en-US", options) +
    ` ${date.getHours().toString().padStart(2, "0")}:${date
      .getMinutes()
      .toString()
      .padStart(2, "0")}`;

  return (
    <div className="card weather-card mt-5">
      <div className="card-body text-center">
        <h5 className="card-title">{formattedDate}</h5>
        <img src={forecast.icon} alt="weather-icon" className="weather-icon" />
        <p className="temp">
          <FaTemperatureHigh /> {forecast.highTemp.toFixed(1)}°C /
          <FaTemperatureLow /> {forecast.lowTemp.toFixed(1)}°C
        </p>
        <ul className="advice-list fw-bold">
          {forecast.weatherAdvice.map((advice, index) => (
            <li key={index}>{advice}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default WeatherCard;
