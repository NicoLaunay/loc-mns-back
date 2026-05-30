package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Loan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.startDate <= current date " +
            "AND loan.endDate <= current date " +
            "AND loan.returnDate = null")
    List<Loan> findAllLate();

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.user.id = :id")
    List<Loan> findAllByUserId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.user.id = :id " +
            "AND loan.returnDate <= current date ")
    List<Loan> findPastByUserId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.user.id = :id " +
            "AND loan.startDate <= current date " +
            "AND loan.returnDate > current date ")
    List<Loan> findOngoingByUserId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.user.id = :id " +
            "AND loan.startDate <= current date " +
            "AND loan.endDate <= current date " +
            "AND loan.returnDate = null")
    List<Loan> findLateByUserId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.user.id = :id " +
            "AND loan.startDate > current date ")
    List<Loan> findPlannedByUserId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.equipment.id = :id")
    List<Loan> findAllByEquipmentId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.equipment.id = :id " +
            "AND loan.returnDate <= current date ")
    List<Loan> findPastByEquipmentId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.equipment.id = :id " +
            "AND loan.startDate <= current date " +
            "AND loan.returnDate > current date ")
    Loan findActiveByEquipmentId(@Param("id") int id);

    @Query("SELECT loan FROM Loan loan " +
            "WHERE loan.equipment.id = :id " +
            "AND loan.startDate > current date ")
    List<Loan> findPlannedByEquipmentId(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Loan loan " +
            "WHERE loan.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") int userId);

}