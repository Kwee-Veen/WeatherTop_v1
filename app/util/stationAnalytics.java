package util;

import models.Reading;

import java.util.List;

public class stationAnalytics {
  public static Reading getLatestReading(List<Reading> inputReading) {
    Reading latestReading = new Reading(-1, -1, -1, -1);
    if (inputReading != null) {
      latestReading = inputReading.get(0);
      for (Reading reading : inputReading) {
        latestReading = reading;
      }
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
}
