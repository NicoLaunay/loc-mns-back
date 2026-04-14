package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Request;
import com.mns.cda.loc_mns.service.RequestService;
import com.mns.cda.loc_mns.view.RequestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
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
        return service.getRequest(id);
    }

    @PostMapping("")
    @JsonView(RequestView.class)
    public ResponseEntity<Request> create(@RequestBody Request newRequest) {
        return service.createRequest(newRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteRequest(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Request requestToUpdate) {
        return service.updateRequest(id, requestToUpdate);
    }

}
