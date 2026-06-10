package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.EquipmentService;
import com.mns.cda.loc_mns.view.EquipmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/equipment")
@CrossOrigin
public class EquipmentController {

    @Autowired
    protected EquipmentService service;

    @GetMapping("/list")
    @JsonView(EquipmentView.class)
    public List<Equipment> getAll() {
        return service.getAll();
    }

    @GetMapping("/list-available-{modelId}")
    @JsonView(EquipmentView.class)
    public List<Equipment> getAllOfModelAvailableOnPeriod(
            @PathVariable Integer modelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return service.getAllOfModelAvailableOnPeriod(modelId, start, end);
    }

    @GetMapping("/{id}")
    @JsonView(EquipmentView.class)
    public ResponseEntity<Equipment> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @JsonView(EquipmentView.class)
    @IsAdmin
    public ResponseEntity<Equipment> create(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody Equipment newEquipment) {
        return new ResponseEntity<>(service.create(newEquipment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Equipment equipmentToUpdate) {
        try {
            service.updateById(id, equipmentToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
