package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.DocumentationDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.service.DocumentationService;
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
public class DocumentationServiceUnitTest {

    @Mock
    private DocumentationDao documentationDao;

    @InjectMocks
    private DocumentationService documentationService;

    // factory to avoid documentation building repetition
    private Documentation buildDocumentation(Integer id) {
        Documentation documentation = new Documentation();
        documentation.setId(id);
        return documentation;
    }

    @Test
    public void getAll_shouldReturnAllDocumentations() {
        List<Documentation> documentations = List.of(buildDocumentation(1), buildDocumentation(2));
        when(documentationDao.findAll()).thenReturn(documentations);

        List<Documentation> result = documentationService.getAll();

        assertEquals(documentations, result);
    }

    @Test
    public void getWithExistingId_shouldReturnDocumentation() {
        Documentation documentation = buildDocumentation(1);
        when(documentationDao.findById(1)).thenReturn(Optional.of(documentation));

        Documentation result = documentationService.get(1);

        assertEquals(documentation, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(documentationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> documentationService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Documentation documentation = buildDocumentation(5);
        when(documentationDao.save(documentation)).thenReturn(documentation);

        Documentation result = documentationService.create(documentation);

        assertNull(documentation.getId());
        assertEquals(documentation, result);
        verify(documentationDao).save(documentation);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(documentationDao.findById(1)).thenReturn(Optional.of(buildDocumentation(1)));

        documentationService.delete(1);

        verify(documentationDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(documentationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> documentationService.delete(99));
        verify(documentationDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(documentationDao.findById(1)).thenReturn(Optional.of(buildDocumentation(1)));
        Documentation update = buildDocumentation(null);

        documentationService.update(1, update);

        assertEquals(1, update.getId());
        verify(documentationDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(documentationDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> documentationService.update(99, buildDocumentation(null)));
        verify(documentationDao, never()).save(any());
    }
}
