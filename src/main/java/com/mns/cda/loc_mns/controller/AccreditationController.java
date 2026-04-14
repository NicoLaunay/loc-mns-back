package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.service.AccreditationService;
import com.mns.cda.loc_mns.view.AccreditationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accreditation")
public class AccreditationController {

    @Autowired
    protected AccreditationService service;

    @GetMapping("/list")
    @JsonView(AccreditationView.class)
    public List<Accreditation> getAll() {
        return service.getAllAccreditations();
    }

    @GetMapping("/{id}")
    @JsonView(AccreditationView.class)
    public ResponseEntity<Accreditation> get(@PathVariable int id) {
        return service.getAccreditation(id);
    }

    @PostMapping("")
    @JsonView(AccreditationView.class)
    public ResponseEntity<Accreditation> create(@RequestBody Accreditation newAccreditation) {
        return service.createAccreditation(newAccreditation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteAccreditation(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Accreditation accreditationToUpdate) {
        return service.updateAccreditation(id, accreditationToUpdate);
    }

}
