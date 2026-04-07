package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

}