package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.EquipmentService;
import com.mns.cda.loc_mns.view.EquipmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public List<Equipment> getAllOfModelAvailableOnPeriod(@PathVariable Integer modelId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return service.getAllOfModelAvailableOnPeriod(modelId, start, end);
    }

    @GetMapping("/{id}")
    @JsonView(EquipmentView.class)
    public ResponseEntity<Equipment> get(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping("")
    @JsonView(EquipmentView.class)
    @IsAdmin // annotation maison
    public ResponseEntity<Equipment> create(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody Equipment newEquipment) {

        //Si on stocke le Creator dans l'equipment :
//        newEquipment.setCreator(userDetails.getUSer());

        return service.create(newEquipment);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteById(id);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Equipment equipmentToUpdate) {
        return service.updateById(id, equipmentToUpdate);
    }

}
