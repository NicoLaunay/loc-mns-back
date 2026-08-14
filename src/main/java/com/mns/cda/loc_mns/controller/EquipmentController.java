package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.dto.EquipmentDto;
import com.mns.cda.loc_mns.dto.EquipmentNoLoansDto;
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
    public List<EquipmentNoLoansDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/with-loans/list")
    @JsonView(EquipmentView.class)
    @IsAdmin
    public List<EquipmentDto> getAllWithLoans() {
        return service.getAllWithLoans();
    }

    @GetMapping("/list-available-{modelId}")
    @JsonView(EquipmentView.class)
    public List<EquipmentNoLoansDto> getAllOfModelAvailableOnPeriod(
            @PathVariable Integer modelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end) {
        return service.getAllOfModelAvailableOnPeriod(modelId, start, end);
    }

    @GetMapping("/{id}")
    @JsonView(EquipmentView.class)
    public ResponseEntity<EquipmentNoLoansDto> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/with-loans/{id}")
    @JsonView(EquipmentView.class)
    @IsAdmin
    public ResponseEntity<EquipmentDto> getWithLoans(@PathVariable int id) {
        return ResponseEntity.ok(service.getWithLoans(id));
    }

    @PostMapping("")
    @JsonView(EquipmentView.class)
    @IsAdmin
    public ResponseEntity<EquipmentDto> create(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody Equipment newEquipment) {
        return ResponseEntity.ok(service.create(newEquipment));
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
                                       @RequestBody Equipment equipmentToUpdate) {
        service.update(id, equipmentToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
