package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.dto.LocationDto;
import com.mns.cda.loc_mns.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Autowired
    protected LocationDao locationDao;

    public List<LocationDto> getAllLocations() {
        return locationDao.findAll()
                .stream()
                .map(location -> new LocationDto(location.getId(), location.getName()))
                .toList();
    }

    public ResponseEntity<Location> getLocation(int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);
        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLocation.get(), HttpStatus.OK);
    }

    public ResponseEntity<Location> createLocation(Location newLocation) {
        newLocation.setId(null);
        locationDao.save(newLocation);
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteLocation(int id) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if (optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        locationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateLocation(int id, Location locationToUpdate) {
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
