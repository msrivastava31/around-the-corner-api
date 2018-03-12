package edu.uw.medhas.aroundthecorner.client;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import edu.uw.medhas.aroundthecorner.dto.Category;
import edu.uw.medhas.aroundthecorner.dto.Location;
import edu.uw.medhas.aroundthecorner.model.PlaceResult;

public class PlacesApiClient extends AbstractHttpJsonClient {
    private final String apiKey;
    private final Integer radius;

    public PlacesApiClient(String userAgent, String baseUrl, String apiKey, Integer radius) {
        super(userAgent, baseUrl);
        this.apiKey = apiKey;
        this.radius = radius;
        setObjectManager();
    }

    @Override
    protected void setObjectManager() {
        this.objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
    
    private PlaceResult searchPlaces(Location location, Category category, Map<String, String> queryParams) {
        final String locationStr = location.getLat() + "," + location.getLng();
        
        queryParams.put("location", locationStr);
        queryParams.put("type", category.getPlaceType());
        queryParams.put("key", apiKey);
        
        return getObject("", PlaceResult.class, queryParams);
    }
    
    public PlaceResult searchPlacesByProminence(Location location, Category category) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("radius", radius.toString());
        
        return searchPlaces(location, category, queryParams);
    }
    
    public PlaceResult searchPlacesByDistance(Location location, Category category) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("rankby", "distance");
        
        return searchPlaces(location, category, queryParams);
    }
}
