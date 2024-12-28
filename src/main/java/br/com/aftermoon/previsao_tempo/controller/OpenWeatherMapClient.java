package br.com.aftermoon.previsao_tempo.controller;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javax.swing.JTextField;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.aftermoon.previsao_tempo.view.InterfaceClient;

public class OpenWeatherMapClient {
	//chave api
	private static final String API_KEY = "418f421fceac868c247c7181098f2a6a";
	//url base do site
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    public void getWeather (String city, JTextField temperatureField, JTextField pressureField, JTextField weatherField, JTextField humidityField, JTextField wind, JTextField cloud){
    	try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = BASE_URL + "?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.body());
                double temperatureMax = root.path("main").path("temp_max").asDouble();
                String pressure = root.path("main").path("pressure").asText();
                String weatherDescription = root.path("weather").get(0).path("description").asText();
                double humidity = root.path("main").path("humidity").asDouble();
                double windSpeed = root.path("wind").path("speed").asDouble();
                double windDirection = root.path("wind").path("deg").asDouble();
                double cloudiness = root.path("clouds").path("all").asDouble();
                
                //estrutura basica da rede bayesiana
                BayesianWeather bayesian = new BayesianWeather();
                //bayesian.setTemperatureMax(temperatureMax);
                bayesian.setPressure(Double.valueOf(pressure));
                bayesian.setHumidity(humidity);
                bayesian.setWindSpeed(windSpeed);
                bayesian.setCloudiness(cloudiness);

                double probabilityRain = bayesian.calcularProbabilidadeChuva();
                //fim da estrutura bayesiana
                
                InterfaceClient.updateProbabilityLabel(String.format("%.2f", probabilityRain) + "%");
                
                System.out.println(windDirection);
                
                temperatureField.setText(temperatureMax + " C°");
                pressureField.setText(pressure + " hPa");
                weatherField.setText(weatherDescription);
                humidityField.setText(humidity + " %");
                wind.setText(String.format("%.2f", windSpeed * 3.6) + " km/h");
                cloud.setText(cloudiness + " %");
            } else {
                System.out.println("Erro: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        	}
    }
}