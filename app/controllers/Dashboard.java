package controllers;
import java.util.List;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller {
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    if (member.stations != null) {
      List<Station> stations = member.stations;
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
  public static void deleteStation(Long id){
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    Logger.info("Deleting station " + station.name + " from member " + member.email);
    redirect("/dashboard");
  }
}

