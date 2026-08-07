package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipmentDao extends JpaRepository<Equipment, Integer> {

    @Query("SELECT e " +
            "FROM Equipment e " +
            "INNER JOIN Loan l ON l.equipment = e " +
            "INNER JOIN AppUser u ON l.user = u " +
            "WHERE u.id = :borrowerId")
    List<Equipment> findAllByBorrowerId(@Param("borrowerId") Integer borrowerId);

    @Query("SELECT e " +
            "FROM Equipment e " +
            "WHERE e.model.id = :modelId " +
            "AND NOT EXISTS (" +
            "   SELECT l FROM Loan l " +
            "   WHERE l.equipment = e " +
            "   AND l.returnDate IS NULL" +
            "   AND l.startDate < :endDate" +
            "   AND l.endDate > :startDate" +
            ")")
    List<Equipment> findAllOfModelAvailableOnPeriod(
            @Param("modelId") Integer modelId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}