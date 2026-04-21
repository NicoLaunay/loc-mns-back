package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.service.CompositionService;
import com.mns.cda.loc_mns.view.CompositionView;
import org.springframework.beans.factory.annotation.Autowired;
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
        Composition.Key key = new Composition.Key(parentId, componentId);
        return service.getComposition(key);
    }

    @PostMapping("")
    @JsonView(CompositionView.class)
    public ResponseEntity<Composition> create(@RequestBody Composition newComposition) {
        return service.createComposition(newComposition);
    }

    @DeleteMapping("/{parentId}/{componentId}")
    public ResponseEntity<Void> delete(@PathVariable int parentId, @PathVariable int componentId) {
        Composition.Key key = new Composition.Key(parentId, componentId);
        return service.deleteComposition(key);
    }

    @PutMapping("/{parentId}/{componentId}")
    public ResponseEntity<Void> update(@PathVariable int parentId,
                                       @PathVariable int componentId,
                                       @RequestBody Composition compositionToUpdate) {
        Composition.Key key = new Composition.Key(parentId, componentId);
        return service.updateComposition(key, compositionToUpdate);
    }

}
