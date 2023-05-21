package util;

import java.time.LocalDateTime;

public class unitConversions {

  /**
   * celciusToFahrenheit takes a temperature double parameter in Celsius and converts it to Fahrenheit.
   * The returned Fahrenheit value is a double, rounded to two decimal places via the rounder method.
   */
  public static double celsiusToFahrenheit(double celsius) {
    return rounder((((celsius * 9) / 5) + 32));
  }

  /**
   * windSpeedToBeaufort takes a wind speed double parameter in km/h, converts it to Beaufort, and returns it as an int.
   */
  public static int windSpeedToBeaufort(double windSpeed) {
    int beaufort = 0;
    if (windSpeed < 1) {
      beaufort = 0;
    } else if (windSpeed <= 5) {
      beaufort = 1;
    } else if (windSpeed <= 11) {
      beaufort = 2;
    } else if (windSpeed <= 19) {
      beaufort = 3;
    } else if (windSpeed <= 28) {
      beaufort = 4;
    } else if (windSpeed <= 38) {
      beaufort = 5;
    } else if (windSpeed <= 49) {
      beaufort = 6;
    } else if (windSpeed <= 61) {
      beaufort = 7;
    } else if (windSpeed <= 74) {
      beaufort = 8;
    } else if (windSpeed <= 88) {
      beaufort = 9;
    } else if (windSpeed <= 102) {
      beaufort = 10;
    } else if (windSpeed <= 117) {
      beaufort = 11;
    }
    return beaufort;
  }

  /**
   * The rounder method takes a double and returns it as a double rounded to two decimal places.
   */
  public static double rounder(double input) {
    double longValue = (double) Math.round(input * 100);
    return longValue / 100;
  }

  /**
   * The dateAndTimeGenerator method generates a date and time without decimal places, in the format desired.
   * It accesses the current time via the java.time package's LocalDateTime.now() method.
   * ints are then extracted for year, month, day, hour, minute and second via the LocalDateTime.now().get methods.
   * A String is constructed and returned combining these int values.
   * The String.format("%02d", x) method ensures that integers below 10 have a leading zero added for consistency.
   */
  public static String dateAndTimeGenerator() {
    LocalDateTime now = LocalDateTime.now();
    int year = now.getYear();
    int month = now.getMonthValue();
    int day = now.getDayOfMonth();
    int hr = now.getHour();
    int min = now.getMinute();
    int sec = now.getSecond();
    return (year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day) + " " + String.format("%02d", hr) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
  }
}
