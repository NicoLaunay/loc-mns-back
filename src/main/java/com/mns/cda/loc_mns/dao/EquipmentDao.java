package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

}