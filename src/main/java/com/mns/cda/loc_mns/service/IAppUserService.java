package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.model.AppUser;

import java.util.List;

public interface IAppUserService {

    List<AppUser> getAll();

    AppUser get(int id);

    AppUser getByEmail(String email);

    AppUser create(AppUser newAppUser);

    void delete(int id);

    void update(int id, AppUser appUserToUpdate);
}
