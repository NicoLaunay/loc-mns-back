package com.mns.cda.loc_mns.mock;

import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.IAppUserService;

import java.util.List;

public class MockAppUserService implements IAppUserService {

    @Override
    public List<AppUser> getAll() {
        return List.of();
    }

    @Override
    public AppUser get(int id) {
        if(id == 1) {
            Role roleAdmin = new Role(1,"ADMIN");

            return new AppUser();
        }
        return null;
    }

    @Override
    public AppUser getByEmail(String email) {
        if(email.equals("john@email.com")) {
            Role roleAdmin = new Role(1,"ADMIN");

            return new AppUser();
        }
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public AppUser create(AppUser newAppUser) {
        return null;
    }

    @Override
    public void delete(int id) {}

    @Override
    public void update(int id, AppUser appUserToUpdate) {}

    @Override
    public void changePassword(int id, String newPassword) {

    }

    @Override
    public void changeRole(int id, Role newRole) {

    }

    @Override
    public void transferOwnership(int idOldOwner, int idNewOwner) {

    }
}
