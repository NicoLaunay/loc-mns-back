package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.exception.IdNotFoundException;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface IAppUserService {

    List<AppUser> getAll();

    AppUser get(int id);

    AppUser getByEmail(String email);

    AppUser create(AppUser newAppUser);

    void delete(int id);

    void update(int id, AppUser appUserToUpdate);

    void changePassword(int id, String newPassword);

    void changeRole(int id, Role newRole);

    void transferOwnership(int idOldOwner, int idNewOwner);
}
