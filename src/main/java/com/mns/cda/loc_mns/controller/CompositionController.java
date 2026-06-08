package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.CompositionService;
import com.mns.cda.loc_mns.view.CompositionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/composition")
@CrossOrigin
public class CompositionController {

    @Autowired
    protected CompositionService service;

    @GetMapping("/list")
    @JsonView(CompositionView.class)
    public List<Composition> getAll() {
        return service.getAllCompositions();
    }

    @GetMapping("/{parentId}/{componentId}")
    @JsonView(CompositionView.class)
    public ResponseEntity<Composition> get(@PathVariable int parentId, @PathVariable int componentId) {
        try {
            Composition.Key key = new Composition.Key(parentId, componentId);
            return new ResponseEntity<>(service.getComposition(key), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @JsonView(CompositionView.class)
    @IsAdmin
    public ResponseEntity<Composition> create(@RequestBody Composition newComposition) {
        return new ResponseEntity<>(service.createComposition(newComposition), HttpStatus.CREATED);
    }

    @DeleteMapping("/{parentId}/{componentId}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int parentId, @PathVariable int componentId) {
        try {
            Composition.Key key = new Composition.Key(parentId, componentId);
            service.deleteComposition(key);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{parentId}/{componentId}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int parentId,
                                       @PathVariable int componentId,
                                       @RequestBody Composition compositionToUpdate) {
        try {
            Composition.Key key = new Composition.Key(parentId, componentId);
            service.updateComposition(key, compositionToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
