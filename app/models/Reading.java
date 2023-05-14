package models;
import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity
public class Reading extends Model
{
    public int code;
    public double temperature;
    public int windSpeed;
    public double windDirection;
    public double pressure;
    public Reading(int code, double temperature, int windSpeed, double windDirection, double pressure) {
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
    }
    public Reading () {
        this.code = 0;
        this.temperature = 0;
        this.windSpeed = 0;
        this.windDirection = 0;
        this.pressure = 0;
    }
    public String latestWeather = "";
}