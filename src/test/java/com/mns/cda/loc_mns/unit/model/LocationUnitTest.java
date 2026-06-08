package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Location;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LocationUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void locationWithBlankName_shouldNotBeValid() {
        Location location = new Location();
        location.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(location),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void locationWithNullName_shouldNotBeValid() {
        Location location = new Location();
        location.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(location),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void locationWithSpacesOnlyName_shouldNotBeValid() {
        Location location = new Location();
        location.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(location),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void locationWithValidName_shouldBeValid() {
        Location location = new Location();
        location.setName("Salle 101");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(location),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }
}
