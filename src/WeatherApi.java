

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {
	private static final String WEATHER_API_KEY = "996c7370741124d7600583f4a705843e";
    private static final String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    private static String fetchWeatherData(String city) {
        try {
            String weatherUrl = String.format("%s?q=%s&appid=%s&units=metric", WEATHER_BASE_URL, city, WEATHER_API_KEY);
            URL url = new URL(weatherUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();
                return response.toString();
            } else {
                System.out.println("Failed to fetch data. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String extractValue(String json, String key, String end) {
        int startIndex = json.indexOf(key);
        if (startIndex == -1) return null;
        startIndex += key.length();
        int endIndex = json.indexOf(end, startIndex);
        return json.substring(startIndex, endIndex);
    }

    public static String getTemperature(String city) {
        String jsonResponse = fetchWeatherData(city);
        if (jsonResponse != null) {
            return extractValue(jsonResponse, "\"temp\":", ",");
        }
        return "Error fetching temperature";
    }
    
    public static String getFeelsLikeTemperature(String city) {
        String jsonResponse = fetchWeatherData(city);
        if (jsonResponse != null) {
            return extractValue(jsonResponse, "\"feels_like\":", ",");
        }
        return "Error fetching feels like temperature";
    }

    public static String getWeatherDescription(String city) {
        String jsonResponse = fetchWeatherData(city);
        if (jsonResponse != null) {
            return extractValue(jsonResponse, "\"description\":\"", "\"");
        }
        return "Error fetching description";
    }

    public static String getCityName(String city) {
        String jsonResponse = fetchWeatherData(city);
        if (jsonResponse != null) {
            return extractValue(jsonResponse, "\"name\":\"", "\"");
        }
        return "Error fetching city name";
    }

}