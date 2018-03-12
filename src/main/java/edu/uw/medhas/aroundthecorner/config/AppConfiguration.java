package edu.uw.medhas.aroundthecorner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.uw.medhas.aroundthecorner.client.PlacesApiClient;

@Configuration
public class AppConfiguration {
    @Value("${api.places.search.radius}")
    private Integer radius;
    
    public Integer getRadius() {
        return radius;
    }
    
    @Bean
    public PlacesApiClient getPlacesApiClient(@Value("${api.user.agent}") String apiUserAgent,
            @Value("${api.places.base.url}") String placesApiBaseUrl,
            @Value("${api.places.key}") String placesApiKey) {
        return new PlacesApiClient(apiUserAgent, placesApiBaseUrl, placesApiKey, radius);
    }
}
