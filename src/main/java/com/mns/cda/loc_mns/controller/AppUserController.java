package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.IAppUserService;
import com.mns.cda.loc_mns.view.AppUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class AppUserController {

    protected final IAppUserService service;

    @GetMapping("/list")
    @JsonView(AppUserView.class)
    @IsAdmin
    public List<AppUser> getAll() {
        return service.getAllAppUsers();
    }

    @GetMapping("/{id}")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getAppUser(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> create(@RequestBody AppUser newAppUser) {
        return new ResponseEntity<>(service.createAppUser(newAppUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteAppUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody AppUser appUserToUpdate) {
        try {
            service.updateAppUser(id, appUserToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
