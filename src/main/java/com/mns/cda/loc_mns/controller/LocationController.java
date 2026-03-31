package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationController {

    @Autowired
    protected LocationDao locationDao;

    @GetMapping("/")
    public String showHome() {
        return "C'est l'Application !";
    }

    @GetMapping("/location/list")
    public List<Location> getAll() {
        return locationDao.findAll();
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> get(@PathVariable int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);
        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLocation.get(), HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        newLocation.setId(null);
        locationDao.save(newLocation);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        locationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        locationToUpdate.setId(id);
        locationDao.save(locationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
