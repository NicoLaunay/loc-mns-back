package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.CompositionDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.service.CompositionService;
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
public class CompositionServiceUnitTest {

    @Mock
    private CompositionDao compositionDao;

    @InjectMocks
    private CompositionService compositionService;

    // composite key used to identify a composition (parent model / component model)
    private Composition.Key buildKey(Integer parentId, Integer componentId) {
        return new Composition.Key(parentId, componentId);
    }

    // factory to avoid composition building repetition
    private Composition buildComposition(Composition.Key key) {
        Composition composition = new Composition();
        composition.setId(key);
        return composition;
    }

    @Test
    public void getAll_shouldReturnAllCompositions() {
        List<Composition> compositions = List.of(
                buildComposition(buildKey(1, 2)),
                buildComposition(buildKey(1, 3))
        );
        when(compositionDao.findAll()).thenReturn(compositions);

        List<Composition> result = compositionService.getAll();

        assertEquals(compositions, result);
    }

    @Test
    public void getWithExistingKey_shouldReturnComposition() {
        Composition.Key key = buildKey(1, 2);
        Composition composition = buildComposition(key);
        when(compositionDao.findById(key)).thenReturn(Optional.of(composition));

        Composition result = compositionService.get(key);

        assertEquals(composition, result);
    }

    @Test
    public void getWithUnknownKey_shouldThrow() {
        Composition.Key key = buildKey(9, 9);
        when(compositionDao.findById(key)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> compositionService.get(key));
    }

    @Test
    public void create_shouldResetKeyAndSave() {
        Composition composition = buildComposition(buildKey(1, 2));
        when(compositionDao.save(composition)).thenReturn(composition);

        Composition result = compositionService.create(composition);

        assertNull(composition.getId());
        assertEquals(composition, result);
        verify(compositionDao).save(composition);
    }

    @Test
    public void deleteWithExistingKey_shouldDelete() {
        Composition.Key key = buildKey(1, 2);
        when(compositionDao.findById(key)).thenReturn(Optional.of(buildComposition(key)));

        compositionService.delete(key);

        verify(compositionDao).deleteById(key);
    }

    @Test
    public void deleteWithUnknownKey_shouldThrowAndNotDelete() {
        Composition.Key key = buildKey(9, 9);
        when(compositionDao.findById(key)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> compositionService.delete(key));
        verify(compositionDao, never()).deleteById(any());
    }

    @Test
    public void updateWithExistingKey_shouldSetKeyAndSave() {
        Composition.Key key = buildKey(1, 2);
        when(compositionDao.findById(key)).thenReturn(Optional.of(buildComposition(key)));
        Composition update = buildComposition(null);

        compositionService.update(key, update);

        assertEquals(key, update.getId());
        verify(compositionDao).save(update);
    }

    @Test
    public void updateWithUnknownKey_shouldThrowAndNotSave() {
        Composition.Key key = buildKey(9, 9);
        when(compositionDao.findById(key)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> compositionService.update(key, buildComposition(null)));
        verify(compositionDao, never()).save(any());
    }
}
