package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.dto.ModelDto;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model")
@CrossOrigin
public class ModelController {

    @Autowired
    protected ModelService service;

    @GetMapping("/list")
    public List<ModelDto> getAll() {
        return service.getAllModels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDto> get(@PathVariable int id) {
        return service.getModel(id);
    }

    @PostMapping("")
    public ResponseEntity<ModelDto> create(@RequestBody Model newModel) {
        return service.createModel(newModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteModel(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Model modelToUpdate) {
        return service.updateModel(id, modelToUpdate);
    }

    @GetMapping("/test")
    public ModelDto test() {
        return new ModelDto(
                1,
                "name",
                "description",
                false,
                new Type(),
                List.of(),
                List.of(),
                List.of()
                );
    }

}
