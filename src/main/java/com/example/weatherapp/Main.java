package com.example.weatherapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Main extends Application {
    private final WeatherApp weatherApp = new WeatherApp();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Weather App");

        // Tworzymy kontrole
        Label cityLabel = new Label("Miasto:");
        TextField cityTextField = new TextField();
        Button getWeatherButton = new Button("Sprawdź pogodę");
        Label temperatureLabel = new Label();
        Label descriptionLabel = new Label();
        Label humidityLabel = new Label();
        Label windSpeedLabel = new Label();
        Label cloudinessLabel = new Label();
        Label iconLabel = new Label();

        // Tworzymy siatkę i ustawiamy w niej kontrole
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(cityLabel, 0, 0);
        gridPane.add(cityTextField, 1, 0);
        gridPane.add(getWeatherButton, 2, 0);
        gridPane.add(temperatureLabel, 0, 1);
        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(humidityLabel, 0, 3);
        gridPane.add(windSpeedLabel, 0, 4);
        gridPane.add(cloudinessLabel, 0, 5);
        gridPane.add(iconLabel, 0, 6);

        // Tworzymy ImageView dla ikony pogodowej
        ImageView iconImageView = new ImageView();
        gridPane.add(iconImageView, 1, 6);

        // Ustawiamy akcję dla przycisku
        getWeatherButton.setOnAction(event -> {
            String city = cityTextField.getText();
            try {
                JSONObject data = weatherApp.getWeather(city);
                if (data != null) {
                    temperatureLabel.setText("Temperatura: " + data.getJSONObject("main").getDouble("temp") + "°C");
                    descriptionLabel.setText("Opis: " + data.getJSONArray("weather").getJSONObject(0).getString("description"));
                    humidityLabel.setText("Wilgotność: " + data.getJSONObject("main").getInt("humidity") + "%");
                    windSpeedLabel.setText("Wiatr: " + data.getJSONObject("wind").getDouble("speed") + " m/s");
                    cloudinessLabel.setText("Zachmurzenie: " + data.getJSONObject("clouds").getInt("all") + "%");
                    String iconCode = data.getJSONArray("weather").getJSONObject(0).getString("icon");

                    String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
                    Image iconImage = new Image(iconUrl);
                    iconImageView.setImage(iconImage);
                } else {
                    temperatureLabel.setText("Unable to retrieve weather data.");
                }
            } catch (JSONException e) {
                temperatureLabel.setText("Unable to retrieve weather data. Please enter a valid city.");
            } catch (IOException e) {
                temperatureLabel.setText("Unable to retrieve weather data. Check your internet connection.");
            } catch (Exception e) {
                temperatureLabel.setText("An unknown error occurred.");
            }
        });

        // Tworzymy scenę i ustawiamy ją jako scenę dla primaryStage
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);

        // Wyświetlamy primaryStage
        primaryStage.show();
    }
}