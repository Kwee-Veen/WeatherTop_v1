package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Reading extends Model
{
    public String code;
    public double temperature;
    public double windSpeed;

    public Reading(String code, double temperature, double windSpeed)
    {
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }
}