package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Type;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TypeUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void typeWithBlankName_shouldNotBeValid() {
        Type type = new Type();
        type.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(type),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void typeWithNullName_shouldNotBeValid() {
        Type type = new Type();
        type.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(type),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void typeWithSpacesOnlyName_shouldNotBeValid() {
        Type type = new Type();
        type.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(type),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void typeWithValidName_shouldBeValid() {
        Type type = new Type();
        type.setName("Informatique");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(type),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }
}
