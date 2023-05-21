package util;

import controllers.Accounts;
import models.Member;
import models.Reading;
import models.Station;

import java.util.List;

public class stationAnalytics {

  /**
   * getLatestReading takes an ArrayList of Readings and returns the most recent Reading from that list.
   */
  public static Reading getLatestReading(List<Reading> inputReadingList) {
    Reading latestReading = inputReadingList.get(0);
    for (Reading reading : inputReadingList) {
      latestReading = reading;
    }
    return latestReading;
  }

  /**
   * getWeather accesses a Station via the id parameter.
   * It sets that Station's latestWeather and latestWeatherIcon fields depending on the code field of that Station's latest Reading.
   * The latestWeatherIcon String corresponds with a FontAwesome icon of the appropriate weather.
   * If no case is met, default latestWeather and latestWeatherIcon values are assigned, indicating no weather found.
   * As this method sets the values of multiple fields, it does not have a return type, instead accessing fields directly.
   */
  public static void getWeather(long id) {
    Station station = Station.findById(id);
    int code = station.latestReading.code;
    switch (code) {
      case 100:
        station.latestWeather = "Clear";
        station.latestWeatherIcon = "fa-solid fa-sun";
        break;
      case 200:
        station.latestWeather = "Partial Clouds";
        station.latestWeatherIcon = "fa-solid fa-cloud-sun";
        break;
      case 300:
        station.latestWeather = "Cloudy";
        station.latestWeatherIcon = "fa-solid fa-cloud";
        break;
      case 400:
        station.latestWeather = "Light Rain";
        station.latestWeatherIcon = "fa-solid fa-cloud-sun-rain";
        break;
      case 500:
        station.latestWeather = "Heavy Shower";
        station.latestWeatherIcon = "fa-solid fa-cloud-showers-heavy";
        break;
      case 600:
        station.latestWeather = "Rain";
        station.latestWeatherIcon = "fa-solid fa-cloud-rain";
        break;
      case 700:
        station.latestWeather = "Snow";
        station.latestWeatherIcon = "fa-solid fa-snowflake";
        break;
      case 800:
        station.latestWeather = "Thunder";
        station.latestWeatherIcon = "fa-solid fa-cloud-bolt";
        break;
      case 900:
        station.latestWeather = "Martian Cloud";
        station.latestWeatherIcon = "fa-solid fa-cloud-moon";
        break;
      case 1000:
        station.latestWeather = "Martian Dust Storm";
        station.latestWeatherIcon = "fa-solid fa-tornado";
        break;
      default:
        station.latestWeather = "No Weather Found";
        station.latestWeatherIcon = "fa-solid fa-circle-xmark";
        break;
    }
  }

  /**
   * getWindDirection takes a double corresponding with a Reading's windDirectionDegrees field.
   * It returns a String corresponding with one of 16 directions within a 360 degree range.
   * If degrees are outside the 360 range, a blank String is returned.
   */
  public static String getWindDirection(double windDirectionDegrees) {
    String windDirection = "";
    if (((windDirectionDegrees <= 11.25) && (windDirectionDegrees >= 0)) || ((windDirectionDegrees >= 348.75) && (windDirectionDegrees <= 360))) {
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

  /**
   * getWindChill takes temperature and wind speed double parameters to calculate wind chill.
   * Wind chill is returned as a double, and rounded to two decimal places via the rounder util method.
   */
  public static double getWindChill(double temperature, double windSpeed) {
    return util.unitConversions.rounder((13.12 + (0.6215 * temperature) - (11.37 * Math.pow(windSpeed, 0.16)) + (0.3965 * temperature * (Math.pow(windSpeed, 0.16)))));
  }

  /**
   * computeLatestStats sets the values of all 'latest-' fields for each Station belonging to the currently logged in Member.
   * If the Station has no Readings, a blank Reading is generated for that Station using the Reading() constructor without arguments,
   * and getWeather assigns default latestWeather and latestWeatherIcon values for that station.
   * If the Station has Readings, the Station's 'latest-' fields, trends, and min-max values are computed through 8 util methods
   * using the Station's Readings and its latest Reading.
   * As this is computed data, it is not saved. Instead, the data is re-computed each time it is required, to avoid stale data.
   */
  public static void computeLatestStats() {
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;
    for (Station station : stations) {
      if (station.readings.isEmpty()) {
        station.latestReading = new Reading();
        getWeather(station.id);
      } else {
        station.latestReading = getLatestReading(station.readings);
        station.latestTemperatureFahrenheit = unitConversions.celsiusToFahrenheit(station.latestReading.temperature);
        station.latestWindSpeedBeaufort = unitConversions.windSpeedToBeaufort(station.latestReading.windSpeed);
        getWeather(station.id);
        station.latestWindDirectionString = getWindDirection(station.latestReading.windDirection);
        station.latestWindChill = getWindChill(station.latestReading.temperature, station.latestReading.windSpeed);
        setMinMaxValues(station.id);
        checkTrends(station.id);
      }
    }
  }

  /**
   * setMinMaxValues accesses a Station via the id parameter, and sets the station's min and max values for
   * the temperature, wind speed & pressure fields to that of the Station's first Reading.
   * It then checks each Reading in the Station against these initial values, changing the Station's min or max
   * values for each of these fields if lesser or larger values are encountered, respectively.
   * As this method sets the values of multiple fields, it does not have a return type, instead accessing fields directly.
   */
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

  /**
   * checkTrends accesses a Station via the id parameter, and checks for trends in all the temperature, wind speed
   * & pressure fields of all readings in that station. When a trend is detected, a String corresponding with
   * a FontAwesome icon trend arrow is stored as temperatureTrendIcon, windTrendIcon or pressureTrendIcon.
   * These variables can be called on the front end as an icon's class to display that trend icon.
   * As this method sets the values of multiple fields, it does not have a return type, instead accessing fields directly.
   */
  public static void checkTrends(long id) {
    Station station = Station.findById(id);
    String positiveTrend = "fa-solid fa-arrow-up";
    String negativeTrend = "fa-solid fa-arrow-down";
    Reading[] readings = new Reading[3];
    for (int i = 0; i < readings.length; i++)
      readings[i] = station.readings.get(0);
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
