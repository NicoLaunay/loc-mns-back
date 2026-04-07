package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.dto.LocationDto;
import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationController {

    @Autowired
    protected LocationService service;

    @GetMapping("/")
    public String showHome() {
        return "C'est l'Application !";
    }

    @GetMapping("/location/list")
    public List<LocationDto> getAll() {
        return service.getAllLocations();
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> get(@PathVariable int id) {
        return service.getLocation(id);
    }

    @PostMapping("/location")
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        return service.createLocation(newLocation);
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteLocation(id);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        return service.updateLocation(id, locationToUpdate);
    }

}
