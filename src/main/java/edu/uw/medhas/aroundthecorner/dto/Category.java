package edu.uw.medhas.aroundthecorner.dto;

public enum Category {
    bank("bank"),
    bar("bar"),
    gas_station("gas_station"),
    bus_station("bus_station"),
    hospital("hospital"),
    hotel("lodging"),
    restaurant("restaurant"),
    grocery("supermarket"),
    pharmacy("pharmacy"),
    parking("parking"),
    movie("movie_theater"),
    cafe("cafe");
    
    private final String placeType;
    
    private Category(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceType() {
        return placeType;
    }
}
