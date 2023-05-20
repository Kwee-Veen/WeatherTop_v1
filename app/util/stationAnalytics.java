package util;

import models.Reading;
import models.Station;

import java.util.List;

public class stationAnalytics {
  public static Reading getLatestReading(List<Reading> inputReading) {
    Reading latestReading = inputReading.get(0);
    for (Reading reading : inputReading) {
      latestReading = reading;
    }
    return latestReading;
  }

  public static void getWeather(long id) {
    Reading reading = Reading.findById(id);
    int code = reading.code;
    switch (code) {
      case 100:
        reading.latestWeather = "Clear";
        reading.latestWeatherIcon = "fa-solid fa-sun";
        break;
      case 200:
        reading.latestWeather = "Partial Clouds";
        reading.latestWeatherIcon = "fa-solid fa-cloud-sun";
        break;
      case 300:
        reading.latestWeather = "Cloudy";
        reading.latestWeatherIcon = "fa-solid fa-cloud";
        break;
      case 400:
        reading.latestWeather = "Light Rain";
        reading.latestWeatherIcon = "fa-solid fa-cloud-sun-rain";
        break;
      case 500:
        reading.latestWeather = "Heavy Shower";
        reading.latestWeatherIcon = "fa-solid fa-cloud-showers-heavy";
        break;
      case 600:
        reading.latestWeather = "Rain";
        reading.latestWeatherIcon = "fa-solid fa-cloud-rain";
        break;
      case 700:
        reading.latestWeather = "Snow";
        reading.latestWeatherIcon = "fa-solid fa-snowflake";
        break;
      case 800:
        reading.latestWeather = "Thunder";
        reading.latestWeatherIcon = "fa-solid fa-cloud-bolt";
        break;
      case 900:
        reading.latestWeather = "Martian Cloud";
        reading.latestWeatherIcon = "fa-solid fa-cloud-moon";
        break;
      case 1000:
        reading.latestWeather = "Martian Dust Storm";
        reading.latestWeatherIcon = "fa-solid fa-tornado";
        break;
      default:
        reading.latestWeather = "No Weather Data";
        reading.latestWeatherIcon = "fa-solid fa-circle-xmark";
        break;
    }
  }

  public static String getWindDirection(double windDirectionDegrees) {
    String windDirection = "";
    if ((windDirectionDegrees <= 11.25) || (windDirectionDegrees >= 348.75)) {
      windDirection = "N";
    } else if (windDirectionDegrees <= 33.75) {
      windDirection = "NNE";
    } else if (windDirectionDegrees <= 56.25) {
      windDirection = "NE";
    } else if (windDirectionDegrees <= 78.75) {
      windDirection = "ENE";
    } else if (windDirectionDegrees <= 101.25) {
      windDirection = "E";
    } else if (windDirectionDegrees <= 123.75) {
      windDirection = "ESE";
    } else if (windDirectionDegrees <= 146.25) {
      windDirection = "SE";
    } else if (windDirectionDegrees <= 168.75) {
      windDirection = "SSE";
    } else if (windDirectionDegrees <= 191.25) {
      windDirection = "S";
    } else if (windDirectionDegrees <= 213.75) {
      windDirection = "SSW";
    } else if (windDirectionDegrees <= 236.25) {
      windDirection = "SW";
    } else if (windDirectionDegrees <= 258.75) {
      windDirection = "WSW";
    } else if (windDirectionDegrees <= 281.25) {
      windDirection = "W";
    } else if (windDirectionDegrees <= 303.75) {
      windDirection = "WNW";
    } else if (windDirectionDegrees <= 326.25) {
      windDirection = "NW";
    } else if (windDirectionDegrees <= 348.75) {
      windDirection = "NNW";
    }
    return windDirection;
  }

  public static double getWindChill(double temperature, double windSpeed) {
    return util.unitConversions.rounder((13.12 + (0.6215 * temperature) - (11.37 * Math.pow(windSpeed, 0.16)) + (0.3965 * temperature * (Math.pow(windSpeed, 0.16)))));
  }

  public static void computeLatestStats(Long id, Station station) {
    if (station.readings.isEmpty()) {
      station.latestReading = new Reading();
      station.latestReading.latestWeatherIcon = "fa-solid fa-circle-xmark";
    } else {
      station.latestReading = getLatestReading(station.readings);
      station.latestTemperatureFahrenheit = unitConversions.celsiusToFahrenheit(station.latestReading.temperature);
      station.latestWindSpeedBeaufort = unitConversions.windSpeedToBeaufort(station.latestReading.windSpeed);
      long latestReadingId = station.latestReading.id;
      stationAnalytics.getWeather(latestReadingId);
      station.latestWindDirectionString = getWindDirection(station.latestReading.windDirection);
      station.latestWindChill = getWindChill(station.latestReading.temperature, station.latestReading.windSpeed);
      setMinMaxValues(id);
      checkTrends(station);
    }
  }

  public static void setMinMaxValues(long id) {
    Station station = Station.findById(id);
    Reading firstReading = station.readings.get(0);
    station.maxTemp = firstReading.temperature;
    station.minTemp = firstReading.temperature;
    station.maxWindSpeed = firstReading.windSpeed;
    station.minWindSpeed = firstReading.windSpeed;
    station.maxPressure = firstReading.pressure;
    station.minPressure = firstReading.pressure;
    for (Reading reading : station.readings) {
      if (reading.temperature > station.maxTemp) station.maxTemp = reading.temperature;
      if (reading.temperature < station.minTemp) station.minTemp = reading.temperature;
      if (reading.windSpeed > station.maxWindSpeed) station.maxWindSpeed = reading.windSpeed;
      if (reading.windSpeed < station.minWindSpeed) station.minWindSpeed = reading.windSpeed;
      if (reading.pressure > station.maxPressure) station.maxPressure = reading.pressure;
      if (reading.pressure < station.minPressure) station.minPressure = reading.pressure;
    }
  }

  public static void checkTrends(Station station) {
    String positiveTrend = "fa-solid fa-arrow-up";
    String negativeTrend = "fa-solid fa-arrow-down";
    Reading[] readings = new Reading[3];
    readings[0] = station.readings.get(0);
    readings[1] = station.readings.get(0);
    readings[2] = station.readings.get(0);

    for (Reading reading : station.readings) {
      readings[0] = readings[1];
      readings[1] = readings[2];
      readings[2] = reading;
    }

    if ((readings[0].temperature < readings[1].temperature) && (readings[1].temperature < readings[2].temperature))
      station.temperatureTrendIcon = positiveTrend;
    else if ((readings[0].temperature > readings[1].temperature) && (readings[1].temperature > readings[2].temperature))
      station.temperatureTrendIcon = negativeTrend;

    if ((readings[0].windSpeed < readings[1].windSpeed) && (readings[1].windSpeed < readings[2].windSpeed))
      station.windTrendIcon = positiveTrend;
    else if ((readings[0].windSpeed > readings[1].windSpeed) && (readings[1].windSpeed > readings[2].windSpeed))
      station.windTrendIcon = negativeTrend;

    if ((readings[0].pressure < readings[1].pressure) && (readings[1].pressure < readings[2].pressure))
      station.pressureTrendIcon = positiveTrend;
    else if ((readings[0].pressure > readings[1].pressure) && (readings[1].pressure > readings[2].pressure))
      station.pressureTrendIcon = negativeTrend;
  }
}
