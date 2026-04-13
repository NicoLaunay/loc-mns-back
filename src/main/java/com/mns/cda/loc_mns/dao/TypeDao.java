package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Role;
import com.mns.cda.loc_mns.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type, Integer> {

}