package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Loan;
import com.mns.cda.loc_mns.model.Request;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class RequestUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- DATE TESTS -------------------------------------------------------

    @Test
    public void requestWithNullDate_shouldNotBeValid() {
        Request request = new Request();
        request.setDate(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "date",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur date n'a pas fonctionné");
    }

    @Test
    public void requestWithValidDate_shouldBeValid() {
        Request request = new Request();
        request.setDate(new Date());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "date",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur date n'aurait pas dû fonctionner");
    }

    // --- CONTENT TESTS -------------------------------------------------------

    @Test
    public void requestWithBlankContent_shouldNotBeValid() {
        Request request = new Request();
        request.setContent("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "content",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur content n'a pas fonctionné");
    }

    @Test
    public void requestWithNullContent_shouldNotBeValid() {
        Request request = new Request();
        request.setContent(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "content",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur content n'a pas fonctionné");
    }

    @Test
    public void requestWithSpacesOnlyContent_shouldNotBeValid() {
        Request request = new Request();
        request.setContent("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "content",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur content n'a pas fonctionné");
    }

    @Test
    public void requestWithValidContent_shouldBeValid() {
        Request request = new Request();
        request.setContent("Demande de prolongation d'emprunt");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "content",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur content n'aurait pas dû fonctionner");
    }

    // --- LOAN TESTS -------------------------------------------------------

    @Test
    public void requestWithNullLoan_shouldNotBeValid() {
        Request request = new Request();
        request.setLoan(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "loan",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur loan n'a pas fonctionné");
    }

    @Test
    public void requestWithValidLoan_shouldBeValid() {
        Request request = new Request();
        request.setLoan(new Loan());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(request),
                "loan",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur loan n'aurait pas dû fonctionner");
    }
}
