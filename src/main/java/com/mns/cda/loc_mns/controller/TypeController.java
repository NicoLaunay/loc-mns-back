package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@CrossOrigin
public class TypeController {

    @Autowired
    protected TypeService service;

    @GetMapping("/list")
    public List<Type> getAll() {
        return service.getAllTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getType(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Type> create(@RequestBody Type newType) {
        return new ResponseEntity<>(service.createType(newType), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Type typeToUpdate) {
        try {
            service.updateType(id, typeToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
