package edu.uw.medhas.aroundthecorner.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceResult {
    private List<PlaceEntity> results;

    public List<PlaceEntity> getResults() {
        return results;
    }

    public void setResults(List<PlaceEntity> results) {
        this.results = results;
    }
    
}
