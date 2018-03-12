package edu.uw.medhas.aroundthecorner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.uw.medhas.aroundthecorner.dto.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Geometry [location=" + location + "]";
    }
}
