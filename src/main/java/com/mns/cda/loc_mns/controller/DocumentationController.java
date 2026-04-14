package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.service.DocumentationService;
import com.mns.cda.loc_mns.view.DocumentationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    @Autowired
    protected DocumentationService service;

    @GetMapping("/list")
    @JsonView(DocumentationView.class)
    public List<Documentation> getAll() {
        return service.getAllDocumentations();
    }

    @GetMapping("/{id}")
    @JsonView(DocumentationView.class)
    public ResponseEntity<Documentation> get(@PathVariable int id) {
        return service.getDocumentation(id);
    }

    @PostMapping("")
    @JsonView(DocumentationView.class)
    public ResponseEntity<Documentation> create(@RequestBody Documentation newDocumentation) {
        return service.createDocumentation(newDocumentation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteDocumentation(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Documentation documentationToUpdate) {
        return service.updateDocumentation(id, documentationToUpdate);
    }

}
