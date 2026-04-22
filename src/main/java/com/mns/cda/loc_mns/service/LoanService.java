package com.mns.cda.loc_mns.service;

import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    @Autowired
    protected LoanDao loanDao;

    public List<Loan> getAllLoans() {
        return loanDao.findAll();
    }

    public ResponseEntity<Loan> getLoan(int id) {
        Optional<Loan> optionalLoan = loanDao.findById(id);
        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLoan.get(), HttpStatus.OK);
    }

    public ResponseEntity<Loan> createLoan(Loan newLoan) {
        newLoan.setId(null);
        loanDao.save(newLoan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteLoan(int id) {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> updateLoan(int id, Loan loanToUpdate) {
        Optional<Loan> optionalLoan = loanDao.findById(id);

        if (optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // On écrase l'id du JSON par celui en paramètre
        loanToUpdate.setId(id);
        loanDao.save(loanToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
