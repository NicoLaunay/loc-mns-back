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

    @GetMapping("/{key}")
    @JsonView(CompositionView.class)
    public ResponseEntity<Composition> get(@PathVariable Composition.Key key) {
        return service.getComposition(key);
    }

    @PostMapping("")
    @JsonView(CompositionView.class)
    public ResponseEntity<Composition> create(@RequestBody Composition newComposition) {
        return service.createComposition(newComposition);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(@PathVariable Composition.Key key) {
        return service.deleteComposition(key);
    }

    @PutMapping("/{key}")
    public ResponseEntity<Void> update(@PathVariable Composition.Key key,
                                       @RequestBody Composition compositionToUpdate) {
        return service.updateComposition(key, compositionToUpdate);
    }

}
