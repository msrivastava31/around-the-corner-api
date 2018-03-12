package edu.uw.medhas.aroundthecorner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Place {
    private Location location;
    private String name;
    private String address;
    private Double distance;
    private Float rating;
    @JsonIgnore
    private Double sortMetric;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Double getSortMetric() {
        return sortMetric;
    }

    public void setSortMetric(Double sortMetric) {
        this.sortMetric = sortMetric;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "Place [location=" + location + ", name=" + name + ", address=" + address + ", distance=" + distance
                + ", rating=" + rating + ", sortMetric=" + sortMetric + "]";
    }
}
