package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends JpaRepository<State, Integer> {

}