package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model {
  public String name = "";
  public double latitude = 0;
  public double longitude = 0;
  public double minTemp = 0;
  public double maxTemp = 0;
  public double minWindSpeed = 0;
  public double maxWindSpeed = 0;
  public double minPressure = 0;
  public double maxPressure = 0;
  @OneToMany(cascade = CascadeType.ALL)
  public List<Reading> readings = new ArrayList<Reading>();
  public Reading latestReading;
  public String latestWeather = "";
  public String latestWeatherIcon = "";
  public double latestTemperatureFahrenheit = 0;
  public int latestWindSpeedBeaufort = 0;
  public String latestWindDirectionString = "";
  public double latestWindChill = 0;
  public String temperatureTrendIcon = "";
  public String windTrendIcon = "";
  public String pressureTrendIcon = "";

  public Station(String name, double latitude, double longitude) {
    this.name = name;
    this.latitude = util.unitConversions.rounder(latitude);
    this.longitude = util.unitConversions.rounder(longitude);
  }
}
