package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Accreditation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccreditationDao extends JpaRepository<Accreditation, Integer> {

}