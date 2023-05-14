package util;

public class unitConversions {
  public static double celsiustoFahrenheit(double celsius) {
    return Math.round(((((celsius * 9) / 5) + 32) * 10) / 10);
  }

  public static int windSpeedToBeaufort(int windSpeed) {
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
}
