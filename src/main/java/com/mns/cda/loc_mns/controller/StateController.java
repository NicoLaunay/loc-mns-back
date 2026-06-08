package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.State;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/state")
@CrossOrigin
public class StateController {

    @Autowired
    protected StateService service;

    @GetMapping("/list")
    public List<State> getAll() {
        return service.getAllStates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getState(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<State> create(@RequestBody State newState) {
        return new ResponseEntity<>(service.createState(newState), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteState(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody State stateToUpdate) {
        try {
            service.updateState(id, stateToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
