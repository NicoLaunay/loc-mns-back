package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.LocationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTest {

    @Mock
    private LocationDao locationDao;

    @InjectMocks
    private LocationService locationService;

    // factory to avoid location building repetition
    private Location buildLocation(Integer id) {
        Location location = new Location();
        location.setId(id);
        return location;
    }

    @Test
    public void getAll_shouldReturnAllLocations() {
        List<Location> locations = List.of(buildLocation(1), buildLocation(2));
        when(locationDao.findAll()).thenReturn(locations);

        List<Location> result = locationService.getAll();

        assertEquals(locations, result);
    }

    @Test
    public void getWithExistingId_shouldReturnLocation() {
        Location location = buildLocation(1);
        when(locationDao.findById(1)).thenReturn(Optional.of(location));

        Location result = locationService.get(1);

        assertEquals(location, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(locationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> locationService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Location location = buildLocation(5);
        when(locationDao.save(location)).thenReturn(location);

        Location result = locationService.create(location);

        assertNull(location.getId());
        assertEquals(location, result);
        verify(locationDao).save(location);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(locationDao.findById(1)).thenReturn(Optional.of(buildLocation(1)));

        locationService.delete(1);

        verify(locationDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(locationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> locationService.delete(99));
        verify(locationDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(locationDao.findById(1)).thenReturn(Optional.of(buildLocation(1)));
        Location update = buildLocation(null);

        locationService.update(1, update);

        assertEquals(1, update.getId());
        verify(locationDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(locationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> locationService.update(99, buildLocation(null)));
        verify(locationDao, never()).save(any());
    }
}
