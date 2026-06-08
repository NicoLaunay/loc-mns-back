package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.AccreditationService;
import com.mns.cda.loc_mns.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accreditation")
@CrossOrigin
@RequiredArgsConstructor
public class AccreditationController {

    protected final AccreditationService service;

    @GetMapping("/list")
    @IsAdmin
    public List<Accreditation> getAll() {
        return service.getAllAccreditations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accreditation> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getAccreditation(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Accreditation> create(@RequestBody Accreditation newAccreditation) {
        return new ResponseEntity<>(service.createAccreditation(newAccreditation), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteAccreditation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @JsonView(Views.Update.class)
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Accreditation accreditationToUpdate) {
        try {
            service.updateAccreditation(id, accreditationToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
