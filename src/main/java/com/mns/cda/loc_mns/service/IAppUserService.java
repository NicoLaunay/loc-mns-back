package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.model.AppUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAppUserService {
    List<AppUser> getAllAppUsers();

    ResponseEntity<AppUser> getAppUser(int id);

    ResponseEntity<AppUser> createAppUser(AppUser newAppUser);

    ResponseEntity<Void> deleteAppUser(int id);

    ResponseEntity<Void> updateAppUser(int id, AppUser appUserToUpdate);
}
