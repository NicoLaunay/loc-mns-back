package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.CompositionService;
import com.mns.cda.loc_mns.view.CompositionView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/composition")
@CrossOrigin
@RequiredArgsConstructor
public class CompositionController {

    protected final CompositionService service;

    @GetMapping("/list")
    @JsonView(CompositionView.class)
    public List<Composition> getAll() {
        return service.getAll();
    }

    @GetMapping("/{parentId}/{componentId}")
    @JsonView(CompositionView.class)
    public ResponseEntity<Composition> get(@PathVariable int parentId, @PathVariable int componentId) {
        return ResponseEntity.ok(service.get(new Composition.Key(parentId, componentId)));
    }

    @PostMapping("")
    @JsonView(CompositionView.class)
    @IsAdmin
    public ResponseEntity<Composition> create(@RequestBody Composition newComposition) {
        return new ResponseEntity<>(service.create(newComposition), HttpStatus.CREATED);
    }

    @DeleteMapping("/{parentId}/{componentId}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int parentId, @PathVariable int componentId) {
        service.delete(new Composition.Key(parentId, componentId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{parentId}/{componentId}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int parentId,
                                       @PathVariable int componentId,
                                       @RequestBody Composition compositionToUpdate) {
        service.update(new Composition.Key(parentId, componentId), compositionToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
