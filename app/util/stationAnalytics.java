package util;

import models.Reading;

import java.util.List;

public class stationAnalytics {
  public static Reading getLatestReading(List<Reading> inputReading) {
      Reading latestReading = inputReading.get(0);
      for (Reading reading : inputReading) {
        latestReading = reading;
    }
    return latestReading;
  }

  public static String getWeather(int weatherCode) {
    switch (weatherCode) {
      case 100:
        return "Clear";
      case 200:
        return "Partial Clouds";
      case 300:
        return "Cloudy";
      case 400:
        return "Light Showers";
      case 500:
        return "Heavy Showers";
      case 600:
        return "Rain";
      case 700:
        return "Snow";
      case 800:
        return "Thunder";
      case 900:
        return "Martian Cloud";
      case 1000:
        return "Martian Dust Storm";
      default:
        return "<No Weather Data Found>";
    }
  }
  public static String getWindDirection(double windDirectionDegrees) {
    String windDirection = "";
    if ((windDirectionDegrees <= 11.25) || (windDirectionDegrees >= 348.75)) {
      windDirection = "North";
    } else if (windDirectionDegrees <= 33.75) {
      windDirection = "North-North-East";
    } else if (windDirectionDegrees <= 56.25) {
      windDirection = "North-East";
    } else if (windDirectionDegrees <= 78.75) {
      windDirection = "East-North-East";
    } else if (windDirectionDegrees <= 101.25) {
      windDirection = "East";
    } else if (windDirectionDegrees <= 123.75) {
      windDirection = "East-South-East";
    } else if (windDirectionDegrees <= 146.25) {
      windDirection = "South-East";
    } else if (windDirectionDegrees <= 168.75) {
      windDirection = "South-South-East";
    } else if (windDirectionDegrees <= 191.25) {
      windDirection = "South";
    } else if (windDirectionDegrees <= 213.75) {
      windDirection = "South-South-West";
    } else if (windDirectionDegrees <= 236.25) {
      windDirection = "South-West";
    } else if (windDirectionDegrees <= 258.75) {
      windDirection = "West-South-West";
    } else if (windDirectionDegrees <= 281.25) {
      windDirection = "West";
    } else if (windDirectionDegrees <= 303.75) {
      windDirection = "West-North-West";
    } else if (windDirectionDegrees <= 326.25) {
      windDirection = "North-West";
    } else if (windDirectionDegrees <= 348.75) {
      windDirection = "North-North-West";
    }
    return windDirection;
  }

  public static double getWindChill(double temperature, int windSpeed) {
    return Math.round(((13.12 + (0.6215*temperature) - (11.37*Math.pow(windSpeed, 0.16)) + (0.3965*temperature*(Math.pow(windSpeed, 0.16)))) * 10) / 10);
  }
}
