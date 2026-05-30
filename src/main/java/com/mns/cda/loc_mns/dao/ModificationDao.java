package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Location;
import com.mns.cda.loc_mns.model.Modification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationDao extends JpaRepository<Modification, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Modification modif " +
            "WHERE modif.author.id = :userId ")
    void deleteAllByUserId(@Param("userId") int userId);
}