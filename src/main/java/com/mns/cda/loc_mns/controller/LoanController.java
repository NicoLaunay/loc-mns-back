package com.mns.cda.loc_mns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.security.AppUserDetails;
import com.mns.cda.loc_mns.security.IsAdmin;
import com.mns.cda.loc_mns.service.AppUserService;
import com.mns.cda.loc_mns.service.LoanService;
import com.mns.cda.loc_mns.view.LoanView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loan")
@CrossOrigin
@RequiredArgsConstructor
public class LoanController {

    protected final LoanService loanService;
    protected final AppUserService userService;

    @GetMapping("/list")
    @JsonView(LoanView.class)
    @IsAdmin
    public List<Loan> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/user{id}")
    @JsonView(LoanView.class)
    @IsAdmin
    public List<Loan> getAllByUserId(@PathVariable int id) {
        return loanService.getAllByUserId(id);
    }

    @GetMapping("/me")
    @JsonView(LoanView.class)
    public List<Loan> getAllOfConnectedUser(@AuthenticationPrincipal AppUserDetails userDetails) {
        return loanService.getAllByUserId(userDetails.getUser().getId());
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
        return ResponseEntity.ok(loanService.get(id));
    }

    @PostMapping
    @JsonView(LoanView.class)
    public ResponseEntity<Loan> create(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody Loan newLoan) throws IllegalArgumentException {
        if (newLoan.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de début ne peut pas être passée");
        }

        AppUser user = userService.getByEmail(userDetails.getUsername());
        newLoan.setUser(user);
        return new ResponseEntity<>(loanService.create(newLoan), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        loanService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody Loan loanToUpdate) {
        loanService.update(id, loanToUpdate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
