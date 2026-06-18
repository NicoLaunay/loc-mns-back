package com.mns.cda.loc_mns.controller;

import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Type;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.AccreditationService;
import com.mns.cda.loc_mns.service.AppUserService;
import com.mns.cda.loc_mns.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@CrossOrigin
@RequiredArgsConstructor
public class TypeController {

    protected final TypeService service;
    protected final AppUserService userService;
    protected final AccreditationService accreditationService;

    @GetMapping("/list")
    public List<Type> getAll() {
        return service.getAll();
    }

    @GetMapping("/borrowable")
    public List<Type> getAllBorrowableByConnectedUser(@AuthenticationPrincipal AppUserDetails userDetails) {
        AppUser user = userService.getByEmail(userDetails.getUsername());
        return service.getAllBorrowableByAccreditation(user.getAccreditation());
    }

    @GetMapping("/borrowable-by/{id}")
    public List<Type> getAllBorrowableByAccreditationId(@PathVariable int id) {
        Accreditation accreditation = accreditationService.get(id);
        return service.getAllBorrowableByAccreditation(accreditation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping("")
    @IsAdmin
    public ResponseEntity<Type> create(@RequestBody Type newType) {
        return new ResponseEntity<>(service.create(newType), HttpStatus.CREATED);
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
                                       @RequestBody Type typeToUpdate) {
        service.update(id, typeToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
