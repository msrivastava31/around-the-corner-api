package edu.uw.medhas.aroundthecorner.service.impl;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.uw.medhas.aroundthecorner.client.PlacesApiClient;
import edu.uw.medhas.aroundthecorner.config.AppConfiguration;
import edu.uw.medhas.aroundthecorner.dto.Category;
import edu.uw.medhas.aroundthecorner.dto.Location;
import edu.uw.medhas.aroundthecorner.dto.Place;
import edu.uw.medhas.aroundthecorner.dto.PlaceList;
import edu.uw.medhas.aroundthecorner.exception.AppException;
import edu.uw.medhas.aroundthecorner.model.PlaceEntity;
import edu.uw.medhas.aroundthecorner.model.PlaceResult;
import edu.uw.medhas.aroundthecorner.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{
    private final PlacesApiClient placesApiClient;
    private final Double maxRadius;
    
    public PlaceServiceImpl(PlacesApiClient placesApiClient, AppConfiguration appConfiguration) {
        this.placesApiClient = placesApiClient;
        maxRadius = Double.valueOf(appConfiguration.getRadius().toString());
    }
    
    @Override
    public PlaceList searchPlaces(String latlngStr, String categoryStr, Integer maxResults) {
        // Validate - Start
        String[] latlngParts = latlngStr.split(",");
        if (latlngParts.length != 2) {
            throw new AppException("Query Param LatLng is invalid");
        }
        
        final Location location = new Location();
        try {
            location.setLat(Double.parseDouble(latlngParts[0].trim()));
            location.setLng(Double.parseDouble(latlngParts[1].trim()));
        } catch (NumberFormatException nfe) {
            throw new AppException("Query Param LatLng is invalid");
        }
        
        Category category = Category.valueOf(categoryStr);
        if (category == null) {
            throw new AppException("Query Param Category is invalid");
        }
        // Validation - End
        
        final PlaceResult placesByProminence = placesApiClient.searchPlacesByProminence(location, category);
        final PlaceResult placesByDistance = placesApiClient.searchPlacesByDistance(location, category);
        
        final List<Place> places = constructPlaceList(location, placesByProminence.getResults(), placesByDistance.getResults());
        int endIndex = (places.size() > maxResults) ? maxResults : places.size();
        
        return new PlaceList(places.subList(0, endIndex));
    }
    
    private List<Place> constructPlaceList(Location location, List<PlaceEntity> prominentPlaces, List<PlaceEntity> nearbyPlaces) {
        final List<Place> places = new ArrayList<>();
        final List<PlaceEntity> placeEntities = new ArrayList<>();
        int maxTypes = 0;
        
        for (PlaceEntity placeEntity : nearbyPlaces) {
            final Double distance = getDistance(location, placeEntity.getGeometry().getLocation());
            if (distance < maxRadius) {
                maxTypes = Math.max(maxTypes, placeEntity.getTypes().size());
                placeEntity.setDistance(distance);
                placeEntities.add(placeEntity);
            }
        }

        final Set<PlaceEntity> prominentPlacesSet = new HashSet<>(prominentPlaces);
        
        int i = 1;
        int size = placeEntities.size();
        for (PlaceEntity placeEntity : placeEntities) {
            final double ratingMetric = ((placeEntity.getRating() == null) ? 2.0d : placeEntity.getRating())/5;
            final double prominenceMetric = (prominentPlacesSet.contains(placeEntity)) ? 1.0d : 0.0d;
            final double positionMetric = 1 - (i++/size);
            
            int numFields = 0;
            if (placeEntity.getRating() != null) {
                numFields++;
            }
            if (StringUtils.hasText(placeEntity.getName())) {
                numFields++;
            }
            if (StringUtils.hasText(placeEntity.getVicinity())) {
                numFields++;
            }
            final float validFieldsMetric = numFields/3;
            
            final double sortMetric = 0.35*ratingMetric + 0.35*prominenceMetric + 0.2*positionMetric + 0.1*validFieldsMetric;
            
            final Place place = new Place();
            place.setName(placeEntity.getName());
            place.setAddress(placeEntity.getVicinity());
            place.setRating(placeEntity.getRating());
            place.setDistance(placeEntity.getDistance());
            place.setLocation(placeEntity.getGeometry().getLocation());
            place.setSortMetric(sortMetric);
            
            places.add(place);
        }
        
        Collections.sort(places, Comparator.comparing(Place::getSortMetric).reversed());
        
        return places;
    }
    
    private Double getDistance(Location location1, Location location2) {
        final int radiusEarth = 6371;
        final double latDistance = Math.toRadians(location1.getLat() - location2.getLat());
        final double lngDistance = Math.toRadians(location1.getLng() - location2.getLng());
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location1.getLat())) * Math.cos(Math.toRadians(location2.getLat()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radiusEarth * c * 1000;
    }
}
