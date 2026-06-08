package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Request;
import com.mns.cda.loc_mns.service.RequestService;
import com.mns.cda.loc_mns.view.RequestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@CrossOrigin
public class RequestController {

    @Autowired
    protected RequestService service;

    @GetMapping("/list")
    @JsonView(RequestView.class)
    public List<Request> getAll() {
        return service.getAllRequests();
    }

    @GetMapping("/{id}")
    @JsonView(RequestView.class)
    public ResponseEntity<Request> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getRequest(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @JsonView(RequestView.class)
    public ResponseEntity<Request> create(@RequestBody Request newRequest) {
        return new ResponseEntity<>(service.createRequest(newRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteRequest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Request requestToUpdate) {
        try {
            service.updateRequest(id, requestToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
