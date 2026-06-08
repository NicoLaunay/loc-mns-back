package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentation")
@CrossOrigin
public class DocumentationController {

    @Autowired
    protected DocumentationService service;

    @GetMapping("/list")
    public List<Documentation> getAll() {
        return service.getAllDocumentations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentation> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getDocumentation(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Documentation> create(@RequestBody Documentation newDocumentation) {
        return new ResponseEntity<>(service.createDocumentation(newDocumentation), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteDocumentation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Documentation documentationToUpdate) {
        try {
            service.updateDocumentation(id, documentationToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
