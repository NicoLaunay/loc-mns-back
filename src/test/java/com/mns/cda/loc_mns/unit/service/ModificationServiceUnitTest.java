package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.ModificationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Modification;
import com.mns.cda.loc_mns.service.ModificationService;
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
public class ModificationServiceUnitTest {

    @Mock
    private ModificationDao modificationDao;

    @InjectMocks
    private ModificationService modificationService;

    // factory to avoid modification building repetition
    private Modification buildModification(Integer id) {
        Modification modification = new Modification();
        modification.setId(id);
        return modification;
    }

    @Test
    public void getAll_shouldReturnAllModifications() {
        List<Modification> modifications = List.of(buildModification(1), buildModification(2));
        when(modificationDao.findAll()).thenReturn(modifications);

        List<Modification> result = modificationService.getAll();

        assertEquals(modifications, result);
    }

    @Test
    public void getWithExistingId_shouldReturnModification() {
        Modification modification = buildModification(1);
        when(modificationDao.findById(1)).thenReturn(Optional.of(modification));

        Modification result = modificationService.get(1);

        assertEquals(modification, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(modificationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modificationService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Modification modification = buildModification(5);
        when(modificationDao.save(modification)).thenReturn(modification);

        Modification result = modificationService.create(modification);

        assertNull(modification.getId());
        assertEquals(modification, result);
        verify(modificationDao).save(modification);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(modificationDao.findById(1)).thenReturn(Optional.of(buildModification(1)));

        modificationService.delete(1);

        verify(modificationDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(modificationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modificationService.delete(99));
        verify(modificationDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(modificationDao.findById(1)).thenReturn(Optional.of(buildModification(1)));
        Modification update = buildModification(null);

        modificationService.update(1, update);

        assertEquals(1, update.getId());
        verify(modificationDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(modificationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modificationService.update(99, buildModification(null)));
        verify(modificationDao, never()).save(any());
    }
}
