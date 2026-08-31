package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.StateDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.State;
import com.mns.cda.loc_mns.service.StateService;
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
public class StateServiceUnitTest {

    @Mock
    private StateDao stateDao;

    @InjectMocks
    private StateService stateService;

    // factory to avoid state building repetition
    private State buildState(Integer id) {
        State state = new State();
        state.setId(id);
        return state;
    }

    @Test
    public void getAll_shouldReturnAllStates() {
        List<State> states = List.of(buildState(1), buildState(2));
        when(stateDao.findAll()).thenReturn(states);

        List<State> result = stateService.getAll();

        assertEquals(states, result);
    }

    @Test
    public void getWithExistingId_shouldReturnState() {
        State state = buildState(1);
        when(stateDao.findById(1)).thenReturn(Optional.of(state));

        State result = stateService.get(1);

        assertEquals(state, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(stateDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> stateService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        State state = buildState(5);
        when(stateDao.save(state)).thenReturn(state);

        State result = stateService.create(state);

        assertNull(state.getId());
        assertEquals(state, result);
        verify(stateDao).save(state);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(stateDao.findById(1)).thenReturn(Optional.of(buildState(1)));

        stateService.delete(1);

        verify(stateDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(stateDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> stateService.delete(99));
        verify(stateDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(stateDao.findById(1)).thenReturn(Optional.of(buildState(1)));
        State update = buildState(null);

        stateService.update(1, update);

        assertEquals(1, update.getId());
        verify(stateDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(stateDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> stateService.update(99, buildState(null)));
        verify(stateDao, never()).save(any());
    }
}
