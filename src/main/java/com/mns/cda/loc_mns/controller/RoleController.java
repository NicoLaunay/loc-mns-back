package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return service.getRole(id);
    }

    @PostMapping("")
    public ResponseEntity<Role> create(@RequestBody Role newRole) {
        return service.createRole(newRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteRole(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Role roleToUpdate) {
        return service.updateRole(id, roleToUpdate);
    }

}
