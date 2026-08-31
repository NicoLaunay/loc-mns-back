package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.EquipmentDao;
import com.mns.cda.loc_mns.dto.EquipmentDto;
import com.mns.cda.loc_mns.dto.EquipmentNoLoansDto;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.mapper.EquipmentMapper;
import com.mns.cda.loc_mns.model.Equipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mns.cda.loc_mns.service.EquipmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceUnitTest {

    @Mock
    private EquipmentDao equipmentDao;

    @Mock
    private EquipmentMapper mapper; // the mapping to DTO is delegated to this collaborator

    @InjectMocks
    private EquipmentService equipmentService;

    private Equipment buildEquipment(Integer id) {
        Equipment equipment = new Equipment();
        equipment.setId(id);
        return equipment;
    }

    private EquipmentNoLoansDto buildNoLoansDto(Integer id) {
        return EquipmentNoLoansDto.builder().id(id).build();
    }

    private EquipmentDto buildDto(Integer id) {
        return EquipmentDto.builder().id(id).build();
    }

    @Test
    public void getAll_shouldReturnMappedNoLoansDtos() {
        Equipment equipment = buildEquipment(1);
        EquipmentNoLoansDto dto = buildNoLoansDto(1);
        when(equipmentDao.findAll()).thenReturn(List.of(equipment));
        when(mapper.toNoLoansDto(equipment)).thenReturn(dto);

        List<EquipmentNoLoansDto> result = equipmentService.getAll();

        assertEquals(List.of(dto), result);
    }

    @Test
    public void getAllWithLoans_shouldReturnMappedDtos() {
        Equipment equipment = buildEquipment(1);
        EquipmentDto dto = buildDto(1);
        when(equipmentDao.findAll()).thenReturn(List.of(equipment));
        when(mapper.toDto(equipment)).thenReturn(dto);

        List<EquipmentDto> result = equipmentService.getAllWithLoans();

        assertEquals(List.of(dto), result);
    }

    @Test
    public void getAllOfModelAvailableOnPeriod_shouldReturnMappedNoLoansDtos() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(3);
        Equipment equipment = buildEquipment(1);
        EquipmentNoLoansDto dto = buildNoLoansDto(1);
        when(equipmentDao.findAllOfModelAvailableOnPeriod(7, start, end)).thenReturn(List.of(equipment));
        when(mapper.toNoLoansDto(equipment)).thenReturn(dto);

        List<EquipmentNoLoansDto> result = equipmentService.getAllOfModelAvailableOnPeriod(7, start, end);

        assertEquals(List.of(dto), result);
    }

    @Test
    public void getWithExistingId_shouldReturnMappedNoLoansDto() {
        Equipment equipment = buildEquipment(1);
        EquipmentNoLoansDto dto = buildNoLoansDto(1);
        when(equipmentDao.findById(1)).thenReturn(Optional.of(equipment));
        when(mapper.toNoLoansDto(equipment)).thenReturn(dto);

        EquipmentNoLoansDto result = equipmentService.get(1);

        assertEquals(dto, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(equipmentDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> equipmentService.get(99));
    }

    @Test
    public void getWithLoansWithExistingId_shouldReturnMappedDto() {
        Equipment equipment = buildEquipment(1);
        EquipmentDto dto = buildDto(1);
        when(equipmentDao.findById(1)).thenReturn(Optional.of(equipment));
        when(mapper.toDto(equipment)).thenReturn(dto);

        EquipmentDto result = equipmentService.getWithLoans(1);

        assertEquals(dto, result);
    }

    @Test
    public void getWithLoansWithUnknownId_shouldThrow() {
        when(equipmentDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> equipmentService.getWithLoans(99));
    }

    @Test
    public void create_shouldResetIdSaveAndReturnMappedDto() {
        Equipment equipment = buildEquipment(5);
        EquipmentDto dto = buildDto(1);
        when(mapper.toDto(equipment)).thenReturn(dto);

        EquipmentDto result = equipmentService.create(equipment);

        assertNull(equipment.getId());
        assertEquals(dto, result);
        verify(equipmentDao).save(equipment);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(equipmentDao.findById(1)).thenReturn(Optional.of(buildEquipment(1)));

        equipmentService.delete(1);

        verify(equipmentDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(equipmentDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> equipmentService.delete(99));
        verify(equipmentDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(equipmentDao.findById(1)).thenReturn(Optional.of(buildEquipment(1)));
        Equipment update = buildEquipment(null);

        equipmentService.update(1, update);

        assertEquals(1, update.getId());
        verify(equipmentDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(equipmentDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> equipmentService.update(99, buildEquipment(null)));
        verify(equipmentDao, never()).save(any());
    }
}
