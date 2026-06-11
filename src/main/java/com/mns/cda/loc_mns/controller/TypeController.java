package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@CrossOrigin
@RequiredArgsConstructor
public class TypeController {

    protected final TypeService service;

    @GetMapping("/list")
    public List<Type> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Type> create(@RequestBody Type newType) {
        return new ResponseEntity<>(service.create(newType), HttpStatus.CREATED);
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
                                       @RequestBody Type typeToUpdate) {
        service.update(id, typeToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
