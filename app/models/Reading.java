package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import static util.unitConversions.dateAndTimeGenerator;

@Entity
public class Reading extends Model {
  public int code;
  public double temperature;
  public double windSpeed;
  public double windDirection;
  public double pressure;
  public String dateAndTime ="";
  public String latestWeather = "";
  public String latestWeatherIcon = "";

  public Reading(int code, double temperature, double windSpeed, double windDirection, double pressure) {
    this.dateAndTime = dateAndTimeGenerator();
    this.code = code;
    this.temperature = util.unitConversions.rounder(temperature);
    this.windSpeed = util.unitConversions.rounder(windSpeed);
    this.windDirection = util.unitConversions.rounder(windDirection);
    this.pressure = util.unitConversions.rounder(pressure);
  }

  public Reading() {
    this.dateAndTime = dateAndTimeGenerator();
    this.code = 0;
    this.temperature = 0;
    this.windSpeed = 0;
    this.windDirection = 0;
    this.pressure = 0;
  }
}