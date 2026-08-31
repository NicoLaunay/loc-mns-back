package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.*;
import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceUnitTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private AppUserDao appUserDao;
    @Mock
    private LoanDao loanDao;
    @Mock
    private RequestDao requestDao;
    @Mock
    private ModificationDao modificationDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private AccreditationDao accreditationDao;

    @InjectMocks
    private AppUserService appUserService;

    private Role buildRole(Integer id, String name) {
        return new Role(id, name);
    }

    // factory building a user carrying the given role
    private AppUser buildUser(Integer id, Role role) {
        AppUser user = new AppUser();
        user.setId(id);
        user.setRole(role);
        return user;
    }

    @Test
    public void getAll_shouldReturnAllUsers() {
        List<AppUser> users = List.of(new AppUser(), new AppUser());
        when(appUserDao.findAll()).thenReturn(users);

        assertEquals(users, appUserService.getAll());
    }

    @Test
    public void getWithExistingId_shouldReturnUser() {
        AppUser user = buildUser(1, null);
        when(appUserDao.findById(1)).thenReturn(Optional.of(user));

        assertEquals(user, appUserService.get(1));
    }

    @Test
    public void getWithUnknownId_shouldThrow() {
        when(appUserDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.get(99));
    }

    @Test
    public void getByExistingEmail_shouldReturnUser() {
        AppUser user = new AppUser();
        when(appUserDao.findByEmail("john@email.com")).thenReturn(Optional.of(user));

        assertEquals(user, appUserService.getByEmail("john@email.com"));
    }

    @Test
    public void getByUnknownEmail_shouldThrow() {
        when(appUserDao.findByEmail("ghost@email.com")).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.getByEmail("ghost@email.com"));
    }

    @Test
    public void create_shouldApplyDefaultsEncodePasswordAndSave() {
        AppUser newUser = new AppUser();
        newUser.setId(5);          // must be reset to null
        newUser.setPassword("raw");

        Role defaultRole = buildRole(3, "USER");
        Accreditation defaultAccreditation = new Accreditation();
        when(roleDao.getReferenceById(3)).thenReturn(defaultRole);
        when(accreditationDao.getReferenceById(2)).thenReturn(defaultAccreditation);
        when(encoder.encode("raw")).thenReturn("encoded");
        when(appUserDao.save(newUser)).thenReturn(newUser);

        AppUser result = appUserService.create(newUser);

        assertNull(newUser.getId());
        assertEquals(defaultRole, newUser.getRole());
        assertEquals(defaultAccreditation, newUser.getAccreditation());
        assertEquals("encoded", newUser.getPassword());
        assertEquals(newUser, result);
        verify(appUserDao).save(newUser);
    }

    @Test
    public void deleteUnknownId_shouldThrowAndNotDelete() {
        when(appUserDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.delete(99));
        verify(appUserDao, never()).deleteById(anyInt());
    }

    @Test
    public void deleteOwner_shouldThrowAccessDeniedAndNotDelete() {
        AppUser owner = buildUser(1, buildRole(1, "OWNER"));
        when(appUserDao.findById(1)).thenReturn(Optional.of(owner));

        assertThrows(AccessDeniedException.class, () -> appUserService.delete(1));
        verify(appUserDao, never()).deleteById(anyInt());
        verifyNoInteractions(loanDao, requestDao, modificationDao);
    }

    @Test
    public void deleteRegularUser_shouldCascadeDeleteRelationsThenUser() {
        AppUser user = buildUser(2, buildRole(3, "USER"));
        when(appUserDao.findById(2)).thenReturn(Optional.of(user));

        appUserService.delete(2);

        verify(modificationDao).deleteAllByUserId(2);
        verify(requestDao).deleteAllByUserId(2);
        verify(loanDao).deleteAllByUserId(2);
        verify(appUserDao).deleteById(2);
    }

    @Test
    public void updateUnknownId_shouldThrowAndNotSave() {
        when(appUserDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.update(99, new AppUser()));
        verify(appUserDao, never()).save(any());
    }

    @Test
    public void updateExistingUser_shouldKeepSensitiveFieldsFromOldUser() {
        Role oldRole = buildRole(1, "OWNER");
        Accreditation oldAccreditation = new Accreditation();
        AppUser oldUser = buildUser(1, oldRole);
        oldUser.setAccreditation(oldAccreditation);
        oldUser.setPassword("oldHash");
        when(appUserDao.findById(1)).thenReturn(Optional.of(oldUser));

        // an update payload trying to change role, accreditation and password
        AppUser update = new AppUser();
        update.setRole(buildRole(3, "USER"));
        update.setPassword("attempt");

        appUserService.update(1, update);

        assertEquals(1, update.getId());
        assertEquals(oldRole, update.getRole());
        assertEquals(oldAccreditation, update.getAccreditation());
        assertEquals("oldHash", update.getPassword());
        verify(appUserDao).save(update);
    }

    @Test
    public void changePassword_shouldEncodeAndSave() {
        AppUser user = buildUser(1, null);
        when(appUserDao.findById(1)).thenReturn(Optional.of(user));
        when(encoder.encode("newPass")).thenReturn("newHash");

        appUserService.changePassword(1, "newPass");

        assertEquals("newHash", user.getPassword());
        verify(appUserDao).save(user);
    }

    @Test
    public void changePasswordUnknownId_shouldThrowAndNotSave() {
        when(appUserDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.changePassword(99, "x"));
        verify(appUserDao, never()).save(any());
    }

    @Test
    public void changeRoleUnknownId_shouldThrowAndNotSave() {
        when(appUserDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.changeRole(99, buildRole(3, "USER")));
        verify(appUserDao, never()).save(any());
    }

    @Test
    public void changeRoleOfOwner_shouldThrowAccessDeniedAndNotSave() {
        AppUser owner = buildUser(1, buildRole(1, "OWNER"));
        when(appUserDao.findById(1)).thenReturn(Optional.of(owner));

        assertThrows(AccessDeniedException.class, () -> appUserService.changeRole(1, buildRole(3, "USER")));
        verify(appUserDao, never()).save(any());
    }

    @Test
    public void changeRoleToOwner_shouldThrowAccessDeniedAndNotSave() {
        AppUser user = buildUser(2, buildRole(3, "USER"));
        when(appUserDao.findById(2)).thenReturn(Optional.of(user));

        assertThrows(AccessDeniedException.class, () -> appUserService.changeRole(2, buildRole(1, "OWNER")));
        verify(appUserDao, never()).save(any());
    }

    @Test
    public void changeRoleWithValidRole_shouldSetRoleAndSave() {
        AppUser user = buildUser(2, buildRole(3, "USER"));
        Role newRole = buildRole(2, "ADMIN");
        when(appUserDao.findById(2)).thenReturn(Optional.of(user));

        appUserService.changeRole(2, newRole);

        assertEquals(newRole, user.getRole());
        verify(appUserDao).save(user);
    }

    @Test
    public void transferOwnership_shouldSwapRolesAndSaveBothUsers() {
        AppUser oldOwner = buildUser(1, buildRole(1, "OWNER"));
        AppUser newOwner = buildUser(2, buildRole(3, "USER"));
        when(appUserDao.findById(1)).thenReturn(Optional.of(oldOwner));
        when(appUserDao.findById(2)).thenReturn(Optional.of(newOwner));

        Role ownerRole = buildRole(1, "OWNER"); // index 0
        Role demotedRole = buildRole(2, "ADMIN"); // index 1
        when(roleDao.findAll()).thenReturn(List.of(ownerRole, demotedRole));

        appUserService.transferOwnership(1, 2);

        assertEquals(demotedRole, oldOwner.getRole());
        assertEquals(ownerRole, newOwner.getRole());
        verify(appUserDao).save(oldOwner);
        verify(appUserDao).save(newOwner);
    }

    @Test
    public void transferOwnershipUnknownUser_shouldThrow() {
        when(appUserDao.findById(1)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> appUserService.transferOwnership(1, 2));
    }
}
