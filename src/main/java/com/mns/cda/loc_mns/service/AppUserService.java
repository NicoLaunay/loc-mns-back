package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    @Autowired
    protected AppUserDao appUserDao;

    public List<AppUser> getAllAppUsers() {
        return appUserDao.findAll();
    }

    public ResponseEntity<AppUser> getAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);
        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalAppUser.get(), HttpStatus.OK);
    }

    public ResponseEntity<AppUser> createAppUser(AppUser newAppUser) {
        newAppUser.setId(null);
        appUserDao.save(newAppUser);
        return new ResponseEntity<>(newAppUser, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteAppUser(int id) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateAppUser(int id, AppUser appUserToUpdate) {
        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        if (optionalAppUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        appUserToUpdate.setId(id);
        appUserDao.save(appUserToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
