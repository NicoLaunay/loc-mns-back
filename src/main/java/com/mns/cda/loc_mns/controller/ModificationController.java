package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Modification;
import com.mns.cda.loc_mns.service.ModificationService;
import com.mns.cda.loc_mns.view.ModificationView;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Modification> getAll() {
        return service.getAllModifications();
    }

    @GetMapping("/{id}")
    @JsonView(ModificationView.class)
    public ResponseEntity<Modification> get(@PathVariable int id) {
        return service.getModification(id);
    }

    @PostMapping("")
    @JsonView(ModificationView.class)
    public ResponseEntity<Modification> create(@RequestBody Modification newModification) {
        return service.createModification(newModification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteModification(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Modification modificationToUpdate) {
        return service.updateModification(id, modificationToUpdate);
    }

}
