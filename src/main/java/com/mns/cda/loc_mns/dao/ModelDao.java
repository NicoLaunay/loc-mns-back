package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelDao extends JpaRepository<Model, Integer> {

    @Query("SELECT model FROM Model model " +
            "WHERE model.type.id = :selectedTypeId")
    List<Model> findAllOfType(@Param("selectedTypeId") Integer selectedTypeId);

}