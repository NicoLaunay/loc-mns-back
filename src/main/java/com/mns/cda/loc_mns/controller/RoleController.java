package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
@RequiredArgsConstructor
public class RoleController {

    protected final RoleService service;

    @GetMapping("/list")
    public List<Role> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Role> create(@RequestBody Role newRole) {
        return new ResponseEntity<>(service.create(newRole), HttpStatus.CREATED);
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
                                       @RequestBody Role roleToUpdate) {
        service.update(id, roleToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
