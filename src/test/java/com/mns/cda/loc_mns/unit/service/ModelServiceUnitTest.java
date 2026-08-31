package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.ModelDao;
import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.mapper.ModelMapper;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.service.ModelService;
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
public class ModelServiceUnitTest {

    @Mock
    private ModelDao modelDao;

    @Mock
    private ModelMapper mapper; // the mapping to DTO is delegated to this collaborator

    @InjectMocks
    private ModelService modelService;

    private Model buildModel(Integer id) {
        Model model = new Model();
        model.setId(id);
        return model;
    }

    private ModelDto buildDto(Integer id) {
        return ModelDto.builder().id(id).build();
    }

    @Test
    public void getAll_shouldReturnMappedDtos() {
        Model model = buildModel(1);
        ModelDto dto = buildDto(1);
        when(modelDao.findAll()).thenReturn(List.of(model));
        when(mapper.toDto(model)).thenReturn(dto);

        List<ModelDto> result = modelService.getAll();

        assertEquals(List.of(dto), result);
    }

    @Test
    public void getAllOfType_shouldReturnMappedDtos() {
        Model model = buildModel(1);
        ModelDto dto = buildDto(1);
        when(modelDao.findAllOfType(4)).thenReturn(List.of(model));
        when(mapper.toDto(model)).thenReturn(dto);

        List<ModelDto> result = modelService.getAllOfType(4);

        assertEquals(List.of(dto), result);
    }

    @Test
    public void getWithExistingId_shouldReturnMappedDto() {
        Model model = buildModel(1);
        ModelDto dto = buildDto(1);
        when(modelDao.findById(1)).thenReturn(Optional.of(model));
        when(mapper.toDto(model)).thenReturn(dto);

        ModelDto result = modelService.get(1);

        assertEquals(dto, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(modelDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modelService.get(99));
    }

    @Test
    public void create_shouldResetIdSaveAndReturnMappedDto() {
        Model model = buildModel(5);
        ModelDto dto = buildDto(1);
        when(mapper.toDto(model)).thenReturn(dto);

        ModelDto result = modelService.create(model);

        assertNull(model.getId());
        assertEquals(dto, result);
        verify(modelDao).save(model);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(modelDao.findById(1)).thenReturn(Optional.of(buildModel(1)));

        modelService.delete(1);

        verify(modelDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(modelDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modelService.delete(99));
        verify(modelDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(modelDao.findById(1)).thenReturn(Optional.of(buildModel(1)));
        Model update = buildModel(null);

        modelService.update(1, update);

        assertEquals(1, update.getId());
        verify(modelDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(modelDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> modelService.update(99, buildModel(null)));
        verify(modelDao, never()).save(any());
    }
}
