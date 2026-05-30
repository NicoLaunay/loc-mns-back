package com.mns.cda.loc_mns.dao;

import com.mns.cda.loc_mns.model.Request;
import com.mns.cda.loc_mns.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDao extends JpaRepository<Request, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Request request " +
            "WHERE request.loan.user.id = :userId ")
    void deleteAllByUserId(@Param("userId") int userId);
}