package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;
@Entity
public class Station extends Model
{
    public String name;
    public double latitude;
    public double longitude;
    public double minTemp;
    public double maxTemp;
    public double minWindSpeed;
    public double maxWindSpeed;
    public double minPressure;
    public double maxPressure;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();
    public Reading latestReading;
    public double latestTemperatureFahrenheit = 0;
    public int latestWindSpeedBeaufort = 0;
    public String latestWindDirectionString = "";
    public double latestWindChill = 0;
    public String temperatureTrendIcon = "";
    public String windTrendIcon = "";
    public String pressureTrendIcon = "";
    public Station(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = util.unitConversions.rounder(latitude);
        this.longitude = util.unitConversions.rounder(longitude);
    }
}
