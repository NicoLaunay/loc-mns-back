package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.State;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StateUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void stateWithBlankName_shouldNotBeValid() {
        State state = new State();
        state.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(state),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void stateWithNullName_shouldNotBeValid() {
        State state = new State();
        state.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(state),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void stateWithSpacesOnlyName_shouldNotBeValid() {
        State state = new State();
        state.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(state),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void stateWithValidName_shouldBeValid() {
        State state = new State();
        state.setName("Disponible");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(state),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }
}
