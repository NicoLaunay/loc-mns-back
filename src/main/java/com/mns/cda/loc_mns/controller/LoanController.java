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
    protected LoanService loanService;

    @GetMapping("/list")
    @JsonView(LoanView.class)
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
        return loanService.getById(id);
    }

    @PostMapping
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> create(@RequestBody Loan newLoan) {
        return loanService.create(newLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return loanService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Loan loanToUpdate) {
        return loanService.update(id, loanToUpdate);
    }

}
