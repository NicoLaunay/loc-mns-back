package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.AccreditationService;
import com.mns.cda.loc_mns.view.AccreditationView;
import com.mns.cda.loc_mns.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getAccreditation(id);
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Accreditation> create(@RequestBody Accreditation newAccreditation) {
        return service.createAccreditation(newAccreditation);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteAccreditation(id);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Update.class)
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Accreditation accreditationToUpdate) {
        return service.updateAccreditation(id, accreditationToUpdate);
    }

}
