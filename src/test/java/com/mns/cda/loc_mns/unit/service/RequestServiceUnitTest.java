package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.RequestDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Request;
import com.mns.cda.loc_mns.service.RequestService;
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
public class RequestServiceUnitTest {

    @Mock
    private RequestDao requestDao;

    @InjectMocks
    private RequestService requestService;

    // factory to avoid request building repetition
    private Request buildRequest(Integer id) {
        Request request = new Request();
        request.setId(id);
        return request;
    }

    @Test
    public void getAll_shouldReturnAllRequests() {
        List<Request> requests = List.of(buildRequest(1), buildRequest(2));
        when(requestDao.findAll()).thenReturn(requests);

        List<Request> result = requestService.getAll();

        assertEquals(requests, result);
    }

    @Test
    public void getWithExistingId_shouldReturnRequest() {
        Request request = buildRequest(1);
        when(requestDao.findById(1)).thenReturn(Optional.of(request));

        Request result = requestService.get(1);

        assertEquals(request, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(requestDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> requestService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Request request = buildRequest(5);
        when(requestDao.save(request)).thenReturn(request);

        Request result = requestService.create(request);

        assertNull(request.getId());
        assertEquals(request, result);
        verify(requestDao).save(request);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(requestDao.findById(1)).thenReturn(Optional.of(buildRequest(1)));

        requestService.delete(1);

        verify(requestDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(requestDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> requestService.delete(99));
        verify(requestDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(requestDao.findById(1)).thenReturn(Optional.of(buildRequest(1)));
        Request update = buildRequest(null);

        requestService.update(1, update);

        assertEquals(1, update.getId());
        verify(requestDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(requestDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> requestService.update(99, buildRequest(null)));
        verify(requestDao, never()).save(any());
    }
}
