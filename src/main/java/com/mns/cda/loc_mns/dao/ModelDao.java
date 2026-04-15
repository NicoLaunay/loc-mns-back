package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelDao extends JpaRepository<Model, Integer> {

}