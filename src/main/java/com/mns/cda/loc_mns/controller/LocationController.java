package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            return new ResponseEntity<>(service.getLocation(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        return new ResponseEntity<>(service.createLocation(newLocation), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteLocation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        try {
            service.updateLocation(id, locationToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
