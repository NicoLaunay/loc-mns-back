package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.IAppUserService;
import com.mns.cda.loc_mns.view.AppUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        return service.getAll();
    }

    @GetMapping("/me")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> getConnected(@AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(service.getByEmail(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> create(@RequestBody AppUser newAppUser) {
        return new ResponseEntity<>(service.create(newAppUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody AppUser appUserToUpdate) {
        service.update(id, appUserToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal AppUserDetails userDetails,
                                               @RequestBody String newPassword) {
        service.changePassword(userDetails.getUser().getId(), newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
