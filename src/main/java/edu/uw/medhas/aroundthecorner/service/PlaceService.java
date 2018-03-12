package edu.uw.medhas.aroundthecorner.service;

import edu.uw.medhas.aroundthecorner.dto.PlaceList;

public interface PlaceService {
    PlaceList searchPlaces(String latlngStr, String categoryStr, Integer maxResults);
}
