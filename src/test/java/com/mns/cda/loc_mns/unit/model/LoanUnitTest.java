package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.model.Loan;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

public class LoanUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- START DATE TESTS -------------------------------------------------------

    @Test
    public void loanWithNullStartDate_shouldNotBeValid() {
        Loan loan = new Loan();
        loan.setStartDate(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "startDate",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur startDate n'a pas fonctionné");
    }

    @Test
    public void loanWithValidStartDate_shouldBeValid() {
        Loan loan = new Loan();
        loan.setStartDate(LocalDate.now());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "startDate",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur startDate n'aurait pas dû fonctionner");
    }

    // --- END DATE TESTS -------------------------------------------------------

    @Test
    public void loanWithNullEndDate_shouldNotBeValid() {
        Loan loan = new Loan();
        loan.setEndDate(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "endDate",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur endDate n'a pas fonctionné");
    }

    @Test
    public void loanWithValidEndDate_shouldBeValid() {
        Loan loan = new Loan();
        loan.setEndDate(LocalDate.now());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "endDate",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur endDate n'aurait pas dû fonctionner");
    }

    // --- USER TESTS -------------------------------------------------------

    @Test
    public void loanWithNullUser_shouldNotBeValid() {
        Loan loan = new Loan();
        loan.setUser(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "user",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur user n'a pas fonctionné");
    }

    @Test
    public void loanWithValidUser_shouldBeValid() {
        Loan loan = new Loan();
        loan.setUser(new AppUser());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "user",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur user n'aurait pas dû fonctionner");
    }

    // --- EQUIPMENT TESTS -------------------------------------------------------

    @Test
    public void loanWithNullEquipment_shouldNotBeValid() {
        Loan loan = new Loan();
        loan.setEquipment(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "equipment",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur equipment n'a pas fonctionné");
    }

    @Test
    public void loanWithValidEquipment_shouldBeValid() {
        Loan loan = new Loan();
        loan.setEquipment(new Equipment());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(loan),
                "equipment",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur equipment n'aurait pas dû fonctionner");
    }
}
