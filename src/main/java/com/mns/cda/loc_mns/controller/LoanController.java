package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.LoanService;
import com.mns.cda.loc_mns.view.LoanView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loan")
@CrossOrigin
public class LoanController {

    @Autowired
    protected LoanService loanService;

    @GetMapping("/list")
    @JsonView(LoanView.class)
    @IsAdmin
    public List<Loan> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/user{id}")
    @JsonView(LoanView.class)
    public List<Loan> getAllByUserId(@PathVariable int id) {
        return loanService.getAllByUserId(id);
    }

    @GetMapping("/user{id}/ended")
    @JsonView(LoanView.class)
    public List<Loan> getPastByUserId(@PathVariable int id) {
        return loanService.getPastByUserId(id);
    }

    @GetMapping("/user{id}/ongoing")
    @JsonView(LoanView.class)
    public List<Loan> getOngoingByUserId(@PathVariable int id) {
        return loanService.getOngoingByUserId(id);
    }

    @GetMapping("/user{id}/planned")
    @JsonView(LoanView.class)
    public List<Loan> getPlannedByUserId(@PathVariable int id) {
        return loanService.getPlannedByUserId(id);
    }

    @GetMapping("/user{id}/late")
    @JsonView(LoanView.class)
    public List<Loan> getLateByUserId(@PathVariable int id) {
        return loanService.getLateByUserId(id);
    }

    @GetMapping("/{id}")
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(loanService.getById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> create(@RequestBody Loan newLoan) {
        try {
            Loan savedLoan = loanService.create(newLoan);
            return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            loanService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Loan loanToUpdate) {
        try {
            loanService.update(id, loanToUpdate);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
