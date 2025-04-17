import axios from "axios";
import { WeatherForecast } from "../types/weather";

const API_URL = "http://localhost:8080/v1/weather";

export const getWeatherForecast = async (
  city: string
): Promise<WeatherForecast[]> => {
  const response = await axios.get<WeatherForecast[]>(`${API_URL}/${city}`);
  return response.data;
};
