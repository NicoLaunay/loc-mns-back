package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    protected RoleService service;

    @GetMapping("/list")
    public List<Role> getAll() {
        return service.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.getRole(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Role> create(@RequestBody Role newRole) {
        return new ResponseEntity<>(service.createRole(newRole), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.deleteRole(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Role roleToUpdate) {
        try {
            service.updateRole(id, roleToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
