package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.State;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/state")
@CrossOrigin
@RequiredArgsConstructor
public class StateController {

    protected final StateService service;

    @GetMapping("/list")
    public List<State> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<State> create(@RequestBody State newState) {
        return new ResponseEntity<>(service.create(newState), HttpStatus.CREATED);
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
                                       @RequestBody State stateToUpdate) {
        service.update(id, stateToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
