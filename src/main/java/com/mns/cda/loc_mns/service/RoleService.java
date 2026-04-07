package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.RoleDao;
import com.mns.cda.loc_mns.dto.RoleDto;
import com.mns.cda.loc_mns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    protected RoleDao roleDao;

    public List<RoleDto> getAllRoles() {
        return roleDao.findAll()
                .stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .toList();
    }

    public ResponseEntity<Role> getRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalRole.get(), HttpStatus.OK);
    }

    public ResponseEntity<Role> createRole(Role newRole) {
        newRole.setId(null);
        roleDao.save(newRole);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteRole(int id) {
        Optional<Role> optionalRole = roleDao.findById(id);

        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateRole(int id, Role roleToUpdate) {
        Optional<Role> optionalRole = roleDao.findById(id);

        if (optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        roleToUpdate.setId(id);
        roleDao.save(roleToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
