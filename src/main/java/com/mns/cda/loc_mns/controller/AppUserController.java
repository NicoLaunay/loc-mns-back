package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.IAppUserService;
import com.mns.cda.loc_mns.view.AppUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class AppUserController {

//    @Autowired
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
        return service.getAppUser(id);
    }

    @PostMapping("")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> create(@RequestBody AppUser newAppUser) {
        return service.createAppUser(newAppUser);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteAppUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody AppUser appUserToUpdate) {
        return service.updateAppUser(id, appUserToUpdate);
    }

}
