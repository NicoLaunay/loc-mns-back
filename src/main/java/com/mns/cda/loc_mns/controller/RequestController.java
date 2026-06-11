package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Request;
import com.mns.cda.loc_mns.service.RequestService;
import com.mns.cda.loc_mns.view.RequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@CrossOrigin
@RequiredArgsConstructor
public class RequestController {

    protected final RequestService service;

    @GetMapping("/list")
    @JsonView(RequestView.class)
    public List<Request> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(RequestView.class)
    public ResponseEntity<Request> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @JsonView(RequestView.class)
    public ResponseEntity<Request> create(@RequestBody Request newRequest) {
        return new ResponseEntity<>(service.create(newRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Request requestToUpdate) {
        service.update(id, requestToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
