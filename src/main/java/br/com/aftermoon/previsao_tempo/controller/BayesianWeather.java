package br.com.aftermoon.previsao_tempo.controller;

import org.apache.commons.math3.distribution.NormalDistribution;

public class BayesianWeather {

    private double pressure;
    private double humidity;
    private double windSpeed;
    private double cloudiness;

    public BayesianWeather() {
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    // calcular a probabilidade de chuva usando lógica bayesiana
    public double calcularProbabilidadeChuva() {
        //distribuição normal para cada variável
        NormalDistribution cloudinessDist = new NormalDistribution(50, 15); // média de 50, desvio de 15
        NormalDistribution humidityDist = new NormalDistribution(70, 10);  // média de 70, desvio de 10
        NormalDistribution windSpeedDist = new NormalDistribution(15, 5);   // média de 15, desvio de 5
        NormalDistribution pressureDist = new NormalDistribution(1010, 5);  // média de 1010 hPa, desvio de 5

        // Usando essas distribuições para calcular a probabilidade de chuva
        double cloudinessProb = cloudinessDist.cumulativeProbability(cloudiness);
        double humidityProb = humidityDist.cumulativeProbability(humidity);
        double windSpeedProb = windSpeedDist.cumulativeProbability(windSpeed);
        double pressureProb = pressureDist.cumulativeProbability(pressure);

        double rainProbability = cloudinessProb * 0.30 + humidityProb * 0.25 +
                                  windSpeedProb * 0.10 + pressureProb * 0.20;

        return Math.min(1.0, rainProbability);  // Garantir que a probabilidade não ultrapasse 1
    }
}
