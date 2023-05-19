package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;
import util.stationAnalytics;

public class Dashboard extends Controller {
  public static void index() {
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;
    Collections.sort(stations, Comparator.comparing(s -> s.name));
    Logger.info("Rendering Dashboard");
    if (member.stations != null) {
      for (Station station : stations) {
        stationAnalytics.computeLatestStats(station.id, station);
      }
      render("dashboard.html", stations);
    } else render("dashboard.html");
  }

  public static void addStation(String name, double latitude, double longitude) {
    Member member = Accounts.getLoggedInMember();
    Station station = new Station(name, latitude, longitude);
    Logger.info("Added new station " + name + " to Member " + member.firstname);
    member.stations.add(station);
    station.save();
    redirect("/dashboard");
  }

  public static void deleteStation(Long id) {
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    Logger.info("Deleting station " + station.name + " from member " + member.email);
    redirect("/dashboard");
  }
}