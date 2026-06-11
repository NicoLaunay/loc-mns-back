package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Documentation;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.DocumentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentation")
@CrossOrigin
@RequiredArgsConstructor
public class DocumentationController {

    protected final DocumentationService service;

    @GetMapping("/list")
    public List<Documentation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentation> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Documentation> create(@RequestBody Documentation newDocumentation) {
        return new ResponseEntity<>(service.create(newDocumentation), HttpStatus.CREATED);
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
                                       @RequestBody Documentation documentationToUpdate) {
        service.update(id, documentationToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
