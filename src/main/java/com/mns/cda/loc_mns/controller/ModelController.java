package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model")
@CrossOrigin
@RequiredArgsConstructor
public class ModelController {

    protected final ModelService service;

    @GetMapping("/list")
    public List<ModelDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/of-type-{typeId}")
    public List<ModelDto> getAllOfType(@PathVariable int typeId) {
        return service.getAllOfType(typeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDto> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<ModelDto> create(@RequestBody Model newModel) {
        return new ResponseEntity<>(service.create(newModel), HttpStatus.CREATED);
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
                                       @RequestBody Model modelToUpdate) {
        service.update(id, modelToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
