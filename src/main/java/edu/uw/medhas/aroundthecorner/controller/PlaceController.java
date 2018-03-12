package edu.uw.medhas.aroundthecorner.controller;

import edu.uw.medhas.aroundthecorner.dto.PlaceList;
import edu.uw.medhas.aroundthecorner.service.PlaceService;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/place")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<PlaceList> searchPlace(@RequestParam(value = "latlng") String latlngStr,
                                 @RequestParam(value = "category") String categoryStr,
                                 @RequestParam(value = "max_results", defaultValue="20") Integer maxResults){
        final Callable<PlaceList> placeListCallable = () -> {
            return placeService.searchPlaces(latlngStr, categoryStr, (maxResults > 20 ? 20 : maxResults));
        };
        
        return placeListCallable;
    }
}
