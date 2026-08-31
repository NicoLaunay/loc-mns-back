package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.RoleService;
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
public class RoleServiceUnitTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleService roleService;

    // factory to avoid role building repetition
    private Role buildRole(Integer id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }

    @Test
    public void getAll_shouldReturnAllRoles() {
        List<Role> roles = List.of(buildRole(1), buildRole(2));
        when(roleDao.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAll();

        assertEquals(roles, result);
    }

    @Test
    public void getWithExistingId_shouldReturnRole() {
        Role role = buildRole(1);
        when(roleDao.findById(1)).thenReturn(Optional.of(role));

        Role result = roleService.get(1);

        assertEquals(role, result);
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(roleDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> roleService.get(99));
    }

    @Test
    public void create_shouldResetIdAndSave() {
        Role role = buildRole(5);
        when(roleDao.save(role)).thenReturn(role);

        Role result = roleService.create(role);

        assertNull(role.getId());
        assertEquals(role, result);
        verify(roleDao).save(role);
    }

    @Test
    public void deleteWithExistingId_shouldDelete() {
        when(roleDao.findById(1)).thenReturn(Optional.of(buildRole(1)));

        roleService.delete(1);

        verify(roleDao).deleteById(1);
    }

    @Test
    public void deleteWithUnknownId_shouldThrowAndNotDelete() {
        when(roleDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> roleService.delete(99));
        verify(roleDao, never()).deleteById(anyInt());
    }

    @Test
    public void updateWithExistingId_shouldSetIdAndSave() {
        when(roleDao.findById(1)).thenReturn(Optional.of(buildRole(1)));
        Role update = buildRole(null);

        roleService.update(1, update);

        assertEquals(1, update.getId());
        verify(roleDao).save(update);
    }

    @Test
    public void updateWithUnknownId_shouldThrowAndNotSave() {
        when(roleDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> roleService.update(99, buildRole(null)));
        verify(roleDao, never()).save(any());
    }
}
