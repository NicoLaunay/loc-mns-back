package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Composition;
import com.mns.cda.loc_mns.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionDao extends JpaRepository<Composition, Composition.Key> {

}