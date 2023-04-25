package com.example.weatherapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherApp {

    private static final String API_KEY = "Your_Api";
    private final Map<String, JSONObject> cache = new HashMap<>();

    public JSONObject getWeather(String city) throws Exception {
        // Sprawdzamy, czy dane są już w cache'u
        if (cache.containsKey(city)) {
            return cache.get(city);
        }

        String urlStr = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, API_KEY);
        URL url = new URL(urlStr);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String responseStr = in.readLine();
        in.close();

        JSONObject data = new JSONObject(responseStr);

        // Zapisujemy dane do cache'u
        cache.put(city, data);

        return data;
    }
}