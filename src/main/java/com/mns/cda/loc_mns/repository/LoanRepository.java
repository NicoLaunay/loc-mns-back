package com.mns.cda.loc_mns.repository;

import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {


}
