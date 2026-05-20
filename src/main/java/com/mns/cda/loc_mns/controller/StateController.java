package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.State;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getState(id);
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<State> create(@RequestBody State newState) {
        return service.createState(newState);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteState(id);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody State stateToUpdate) {
        return service.updateState(id, stateToUpdate);
    }

}
