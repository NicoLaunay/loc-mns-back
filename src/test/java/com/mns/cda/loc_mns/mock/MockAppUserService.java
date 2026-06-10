package com.mns.cda.loc_mns.mock;

import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.IAppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class MockAppUserService implements IAppUserService {

    @Override
    public List<AppUser> getAllAppUsers() {
        return List.of();
    }

    @Override
    public AppUser getAppUser(int id) {
        if(id == 1) {
            Role roleAdmin = new Role(1,"ADMIN");

            return new AppUser();
        }
        return null;
    }

    @Override
    public AppUser getAppUserByEmail(String email) {
        if(email.equals("john@email.com")) {
            Role roleAdmin = new Role(1,"ADMIN");

            return new AppUser();
        }
        return null;
    }

    @Override
    public AppUser createAppUser(AppUser newAppUser) {
        return null;
    }

    @Override
    public void deleteAppUser(int id) {}

    @Override
    public void updateAppUser(int id, AppUser appUserToUpdate) {}
}
