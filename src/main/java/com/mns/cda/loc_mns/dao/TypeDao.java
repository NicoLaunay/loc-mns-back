package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {

}