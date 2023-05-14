package models;
import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity
public class Reading extends Model
{
    public int code;
    public double temperature;
    public int windSpeed;
    public double pressure;
    public Reading(int code, double temperature, int windSpeed, double pressure) {
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
    }
}