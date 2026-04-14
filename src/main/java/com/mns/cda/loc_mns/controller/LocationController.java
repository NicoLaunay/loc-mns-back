package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.service.LocationService;
import com.mns.cda.loc_mns.view.LocationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    protected LocationService service;

    @GetMapping("/list")
    @JsonView(LocationView.class)
    public List<Location> getAll() {
        return service.getAllLocations();
    }

    @GetMapping("/{id}")
    @JsonView(LocationView.class)
    public ResponseEntity<Location> get(@PathVariable int id) {
        return service.getLocation(id);
    }

    @PostMapping("")
    @JsonView(LocationView.class)
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        return service.createLocation(newLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteLocation(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        return service.updateLocation(id, locationToUpdate);
    }

}
