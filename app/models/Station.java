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
    public double latestTemperatureFahrenheit = 0;
    public int latestWindSpeedBeaufort = 0;

    public String latestWindDirectionString ="";
    public double latestWindChill = 0;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();
    public Reading latestReading;
    public Station(String name)
    {
        this.name = name;
    }
}
