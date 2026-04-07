package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.dto.RoleDto;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    protected RoleService service;

    @GetMapping("/role/list")
    public List<RoleDto> getAll() {
        return service.getAllRoles();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {
        return service.getRole(id);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@RequestBody Role newRole) {
        return service.createRole(newRole);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteRole(id);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Role roleToUpdate) {
        return service.updateRole(id, roleToUpdate);
    }

}
