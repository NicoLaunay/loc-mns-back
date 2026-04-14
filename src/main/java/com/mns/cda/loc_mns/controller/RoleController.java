package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.service.RoleService;
import com.mns.cda.loc_mns.view.RoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    protected RoleService service;

    @GetMapping("/list")
    @JsonView(RoleView.class)
    public List<Role> getAll() {
        return service.getAllRoles();
    }

    @GetMapping("/{id}")
    @JsonView(RoleView.class)
    public ResponseEntity<Role> get(@PathVariable int id) {
        return service.getRole(id);
    }

    @PostMapping("")
    @JsonView(RoleView.class)
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
