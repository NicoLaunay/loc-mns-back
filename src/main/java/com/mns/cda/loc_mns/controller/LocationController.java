package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin
public class LocationController {

    @Autowired
    protected LocationService service;

    @GetMapping("/list")
    public List<Location> getAll() {
        return service.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> get(@PathVariable int id) {
        return service.getLocation(id);
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        return service.createLocation(newLocation);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteLocation(id);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        return service.updateLocation(id, locationToUpdate);
    }

}
