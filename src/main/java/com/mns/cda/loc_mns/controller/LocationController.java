package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin
@RequiredArgsConstructor
public class LocationController {

    protected final LocationService service;

    @GetMapping("/list")
    public List<Location> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Location> create(@RequestBody Location newLocation) {
        return new ResponseEntity<>(service.create(newLocation), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Location locationToUpdate) {
        service.update(id, locationToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
