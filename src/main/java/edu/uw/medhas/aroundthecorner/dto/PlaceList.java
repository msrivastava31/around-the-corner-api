package edu.uw.medhas.aroundthecorner.dto;

import java.util.Collections;
import java.util.List;

public class PlaceList {
    private final List<Place> placeList;
    
    public PlaceList(List<Place> placeList) {
        this.placeList = Collections.unmodifiableList(placeList);
    }
    
    public List<Place> getPlaceList() {
        return placeList;
    }
}
