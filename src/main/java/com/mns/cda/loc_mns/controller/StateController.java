package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.dto.StateDto;
import com.mns.cda.loc_mns.model.State;
import com.mns.cda.loc_mns.service.StateService;
import com.mns.cda.loc_mns.view.StateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    protected StateService service;

    @GetMapping("/list")
    @JsonView(StateView.class)
    public List<State> getAll() {
        return service.getAllStates();
    }

    @GetMapping("/{id}")
    @JsonView(StateView.class)
    public ResponseEntity<State> get(@PathVariable int id) {
        return service.getState(id);
    }

    @PostMapping("")
    @JsonView(StateView.class)
    public ResponseEntity<State> create(@RequestBody State newState) {
        return service.createState(newState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteState(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody State stateToUpdate) {
        return service.updateState(id, stateToUpdate);
    }

}
