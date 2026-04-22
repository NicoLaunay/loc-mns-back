package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.service.LoanService;
import com.mns.cda.loc_mns.view.LoanView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@CrossOrigin
public class LoanController {

    @Autowired
    protected LoanService service;

    @GetMapping("/list")
    @JsonView(LoanView.class)
    public List<Loan> getAll() {
        return service.getAllLoans();
    }

    @GetMapping("/{id}")
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> get(@PathVariable int id) {
        return service.getLoan(id);
    }

    @PostMapping
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> create(@RequestBody Loan newLoan) {
        return service.createLoan(newLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return service.deleteLoan(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Loan loanToUpdate) {
        return service.updateLoan(id, loanToUpdate);
    }

}
