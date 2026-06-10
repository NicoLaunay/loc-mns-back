package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.model.AppUser;

import java.util.List;

public interface IAppUserService {

    List<AppUser> getAllAppUsers();

    AppUser getAppUser(int id);

    AppUser getAppUserByEmail(String email);

    AppUser createAppUser(AppUser newAppUser);

    void deleteAppUser(int id);

    void updateAppUser(int id, AppUser appUserToUpdate);
}
