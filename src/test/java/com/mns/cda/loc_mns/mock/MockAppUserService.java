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
    public ResponseEntity<AppUser> getAppUser(int id) {
        if(id == 1) {
            Role roleAdmin = new Role(1,"ADMIN");
            AppUser fakeUser = new AppUser();

            return new ResponseEntity(Optional.of(fakeUser), HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<AppUser> createAppUser(AppUser newAppUser) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAppUser(int id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateAppUser(int id, AppUser appUserToUpdate) {
        return null;
    }
}
