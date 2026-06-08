package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Modification;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.ModificationService;
import com.mns.cda.loc_mns.view.ModificationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modification")
@CrossOrigin
public class ModificationController {

    @Autowired
    protected ModificationService service;

    @GetMapping("/list")
    @JsonView(ModificationView.class)
    @IsAdmin
    public List<Modification> getAll() {
        return service.getAllModifications();
    }

    @GetMapping("/{id}")
    @JsonView(ModificationView.class)
    @IsAdmin
    public ResponseEntity<Modification> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getModification(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @JsonView(ModificationView.class)
    @IsAdmin
    public ResponseEntity<Modification> create(@RequestBody Modification newModification) {
        return new ResponseEntity<>(service.createModification(newModification), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteModification(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Modification modificationToUpdate) {
        try {
            service.updateModification(id, modificationToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
