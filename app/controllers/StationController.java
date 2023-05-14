package controllers;

import models.Station;
import play.Logger;
import play.mvc.Controller;
import util.stationAnalytics;
import util.unitConversions;

public class StationController extends Controller {
  public static void index(long id) {
    Station station = Station.findById(id);
    Logger.info("Rendering station" + id);
    station.latestReading = stationAnalytics.getLatestReading(station.readings);
    station.latestTemperatureFahrenheit = unitConversions.celsiustoFahrenheit(station.latestReading.temperature);
    station.latestWindSpeedBeaufort = unitConversions.windSpeedToBeaufort(station.latestReading.windSpeed);
    String latestWeather = stationAnalytics.getWeather(station.latestReading.code);
    render("station.html", station, latestWeather);
  }
}