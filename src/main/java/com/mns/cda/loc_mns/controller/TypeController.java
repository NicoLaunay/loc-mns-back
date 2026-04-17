package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getType(id);
    }

    @PostMapping("")
    public ResponseEntity<Type> create(@RequestBody Type newType) {
        return service.createType(newType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteType(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Type typeToUpdate) {
        return service.updateType(id, typeToUpdate);
    }

}
