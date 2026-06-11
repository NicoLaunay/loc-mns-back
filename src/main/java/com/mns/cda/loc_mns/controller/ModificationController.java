package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Modification;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.ModificationService;
import com.mns.cda.loc_mns.view.ModificationView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modification")
@CrossOrigin
@RequiredArgsConstructor
public class ModificationController {

    protected final ModificationService service;

    @GetMapping("/list")
    @JsonView(ModificationView.class)
    @IsAdmin
    public List<Modification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(ModificationView.class)
    @IsAdmin
    public ResponseEntity<Modification> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @JsonView(ModificationView.class)
    @IsAdmin
    public ResponseEntity<Modification> create(@RequestBody Modification newModification) {
        return new ResponseEntity<>(service.create(newModification), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Modification modificationToUpdate) {
        service.update(id, modificationToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
