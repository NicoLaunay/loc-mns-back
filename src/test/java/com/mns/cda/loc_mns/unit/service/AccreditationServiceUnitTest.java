package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.AccreditationDao;
import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.service.AccreditationService;
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
public class AccreditationServiceUnitTest {

    @Mock
    private AccreditationDao accreditationDao;

    @Mock
    private TypeDao typeDao; // injected even if unused here, because required by the actual service

    @InjectMocks
    private AccreditationService accreditationService;

    // factory to avoid accreditation building repetition
    private Accreditation buildAccreditation(Integer id) {
        Accreditation accreditation = new Accreditation();
        accreditation.setId(id);
        return accreditation;
    }

    @Test
    public void getAll_shouldReturnAllAccreditations() {
        List<Accreditation> accreditations = List.of(buildAccreditation(1), buildAccreditation(2));
        when(accreditationDao.findAll()).thenReturn(accreditations);

        List<Accreditation> result = accreditationService.getAll();

        assertEquals(accreditations, result);
    }

    @Test
    public void getWithExistingId_shouldReturnAccreditation() {
        Accreditation accreditation = buildAccreditation(1);
        when(accreditationDao.findById(1)).thenReturn(Optional.of(accreditation));

        Accreditation result = accreditationService.get(1);

        assertEquals(accreditation, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(accreditationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> accreditationService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        // id provided by the client must be ignored (null before save)
        Accreditation accreditation = buildAccreditation(5);
        when(accreditationDao.save(accreditation)).thenReturn(accreditation);

        Accreditation result = accreditationService.create(accreditation);

        assertNull(accreditation.getId());
        assertEquals(accreditation, result);
        verify(accreditationDao).save(accreditation);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(accreditationDao.findById(1)).thenReturn(Optional.of(buildAccreditation(1)));

        accreditationService.delete(1);

        verify(accreditationDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(accreditationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> accreditationService.delete(99));
        verify(accreditationDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(accreditationDao.findById(1)).thenReturn(Optional.of(buildAccreditation(1)));
        Accreditation update = buildAccreditation(null);

        accreditationService.update(1, update);

        assertEquals(1, update.getId());
        verify(accreditationDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(accreditationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> accreditationService.update(99, buildAccreditation(null)));
        verify(accreditationDao, never()).save(any());
    }
}