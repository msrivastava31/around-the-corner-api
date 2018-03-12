package edu.uw.medhas.aroundthecorner.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceEntity {
    private String placeId;
    private String name;
    private Float rating;
    private Double distance;
    private Geometry geometry;
    private String vicinity;
    private List<String> types;
    
    public String getPlaceId() {
        return placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public Geometry getGeometry() {
        return geometry;
    }
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    public String getVicinity() {
        return vicinity;
    }
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    public List<String> getTypes() {
        return types;
    }
    public void setTypes(List<String> types) {
        this.types = types;
    }
    
    @Override
    public int hashCode() {
        return getPlaceId().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceEntity)) {
            return false;
        }
        
        final PlaceEntity placeEntityObj = (PlaceEntity) obj;
        return getPlaceId().equals(placeEntityObj.getPlaceId());
    }
    
    @Override
    public String toString() {
        return "PlaceEntity [placeId=" + placeId + ", name=" + name + ", rating=" + rating + ", geometry=" + geometry
                + ", vicinity=" + vicinity + ", types=" + types + "]";
    }
    
}
