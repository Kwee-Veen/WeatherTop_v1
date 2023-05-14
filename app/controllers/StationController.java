package controllers;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import util.stationAnalytics;
import util.unitConversions;

public class StationController extends Controller {
  public static void index(long id) {
    Station station = Station.findById(id);
    Logger.info("Rendering station " + station.name);
    if (station.readings.isEmpty()) {
      station.latestReading = new Reading();
    } else {
      station.latestReading = stationAnalytics.getLatestReading(station.readings);
      station.latestTemperatureFahrenheit = unitConversions.celsiusToFahrenheit(station.latestReading.temperature);
      station.latestWindSpeedBeaufort = unitConversions.windSpeedToBeaufort(station.latestReading.windSpeed);
      long latestReadingId = station.latestReading.id;
      stationAnalytics.getWeather(latestReadingId);
      station.latestWindDirectionString = stationAnalytics.getWindDirection(station.latestReading.windDirection);
      station.latestWindChill = stationAnalytics.getWindChill(station.latestReading.temperature, station.latestReading.windSpeed);
      stationAnalytics.setMinMaxValues(id);
    }
    render("station.html", station);
  }
  public static void addReading(Long id, int code, double temperature, double windSpeed, double windDirection, double pressure) {
    Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure);
    Station station = Station.findById(id);
    station.readings.add(reading);
    Logger.info("Added new reading to station " + station.name);
    station.save();
    redirect("/station/" + id);
  }
}