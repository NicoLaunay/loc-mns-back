package com.mns.cda.loc_mns.unit.service;

import com.mns.cda.loc_mns.dao.AppUserDao;
import com.mns.cda.loc_mns.dao.LoanDao;
import com.mns.cda.loc_mns.exception.IncoherentDateException;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.service.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceUnitTest {

    @Mock
    private LoanDao loanDao;

    @Mock
    private AppUserDao userDao; // injected even if unused here, because imported in the actual service

    @InjectMocks
    private LoanService loanService;

    // factory to avoid loan building repetition
    private Loan buildLoan(LocalDate start, LocalDate end) {
        Equipment equipment = new Equipment();
        equipment.setId(1);

        Loan loan = new Loan();
        loan.setEquipment(equipment);
        loan.setStartDate(start);
        loan.setEndDate(end);
        return loan;
    }

    @Test
    public void createWithEndDateBeforeStartDate_shouldThrowAndNotSave() {
        // end date before start date
        Loan loan = buildLoan(LocalDate.now().plusDays(5), LocalDate.now().plusDays(2));

        assertThrows(IncoherentDateException.class, () -> loanService.create(loan));
        verify(loanDao, never()).save(any());
    }

    @Test
    public void createWhenEquipmentAlreadyBooked_shouldThrowAndNotSave() {
        Loan loan = buildLoan(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        // simulate overlap
        when(loanDao.existsOverlappingByEquipmentId(1, loan.getStartDate(), loan.getEndDate()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> loanService.create(loan));
        verify(loanDao, never()).save(any());
    }

    @Test
    public void createWithValidLoan_shouldSaveAndReturnLoan() {
        Loan loan = buildLoan(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));

        // no overlap -> define what the save returns
        when(loanDao.existsOverlappingByEquipmentId(1, loan.getStartDate(), loan.getEndDate()))
                .thenReturn(false);
        when(loanDao.save(loan)).thenReturn(loan);

        Loan result = loanService.create(loan);

        assertEquals(loan, result);
        verify(loanDao).save(loan);
    }
}