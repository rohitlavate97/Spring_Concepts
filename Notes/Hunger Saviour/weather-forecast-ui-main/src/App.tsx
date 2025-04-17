import React, { useState, useEffect } from "react";
import "./App.css";
import { WeatherForecast } from "./types/weather";
import { getWeatherForecast } from "./services/weatherService";
import WeatherCard from "./components/WeatherCard";
import SearchBar from "./components/SearchBar";

const App: React.FC = () => {
  const [city, setCity] = useState("Mumbai");
  const [weatherData, setWeatherData] = useState<WeatherForecast[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchWeather = async () => {
      setLoading(true);
      setError("");
      try {
        const data = await getWeatherForecast(city);
        setWeatherData(data);
      } catch (err) {
        setError("Error fetching weather data");
      } finally {
        setLoading(false);
      }
    };
    fetchWeather();
  }, [city]);

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4 fw-bold">Weather Forecast</h1>
      <SearchBar onSearch={setCity} />
      {loading && <p className="text-center">Loading...</p>}
      {error && <p className="text-center text-danger">{error}</p>}
      <div className="weather-container">
        {weatherData.map((forecast) => (
          <WeatherCard key={forecast.date} forecast={forecast} />
        ))}
      </div>
    </div>
  );
};

export default App;
