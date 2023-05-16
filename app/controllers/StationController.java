package controllers;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import util.stationAnalytics;

public class StationController extends Controller {
  public static void index(long id) {
    Station station = Station.findById(id);
    Logger.info("Rendering station " + station.name);
    stationAnalytics.computeLatestStats(id, station);
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

  public static void deleteReading(Long id, Long readingid) {
    Station station = Station.findById(id);
    Reading reading = Reading.findById(readingid);
    station.readings.remove(reading);
    station.save();
    reading.delete();
    stationAnalytics.computeLatestStats(id, station);
    Logger.info("Deleting reading " + reading.id + " from station " + station.name);
    render("station.html", station);
  }
}