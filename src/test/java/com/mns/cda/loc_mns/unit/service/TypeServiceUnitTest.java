package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.TypeDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.service.TypeService;
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
public class TypeServiceUnitTest {

    @Mock
    private TypeDao typeDao;

    @InjectMocks
    private TypeService typeService;

    // factory to avoid type building repetition
    private Type buildType(Integer id) {
        Type type = new Type();
        type.setId(id);
        return type;
    }

    @Test
    public void getAll_shouldReturnAllTypes() {
        List<Type> types = List.of(buildType(1), buildType(2));
        when(typeDao.findAll()).thenReturn(types);

        List<Type> result = typeService.getAll();

        assertEquals(types, result);
    }

    @Test
    public void getAllBorrowableByAccreditation_shouldReturnBorrowedTypes() {
        // this method reads the list directly from the accreditation, without hitting the dao
        List<Type> borrowed = List.of(buildType(1), buildType(2));
        Accreditation accreditation = new Accreditation();
        accreditation.setBorrowedTypes(borrowed);

        List<Type> result = typeService.getAllBorrowableByAccreditation(accreditation);

        assertEquals(borrowed, result);
        verifyNoInteractions(typeDao);
    }

    @Test
    public void getWithExistingId_shouldReturnType() {
        Type type = buildType(1);
        when(typeDao.findById(1)).thenReturn(Optional.of(type));

        Type result = typeService.get(1);

        assertEquals(type, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(typeDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> typeService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Type type = buildType(5);
        when(typeDao.save(type)).thenReturn(type);

        Type result = typeService.create(type);

        assertNull(type.getId());
        assertEquals(type, result);
        verify(typeDao).save(type);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(typeDao.findById(1)).thenReturn(Optional.of(buildType(1)));

        typeService.delete(1);

        verify(typeDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(typeDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> typeService.delete(99));
        verify(typeDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(typeDao.findById(1)).thenReturn(Optional.of(buildType(1)));
        Type update = buildType(null);

        typeService.update(1, update);

        assertEquals(1, update.getId());
        verify(typeDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(typeDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> typeService.update(99, buildType(null)));
        verify(typeDao, never()).save(any());
    }
}
