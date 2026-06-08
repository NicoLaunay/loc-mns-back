package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Model;
import com.mns.cda.loc_mns.model.Type;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ModelUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void modelWithBlankName_shouldNotBeValid() {
        Model model = new Model();
        model.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void modelWithNullName_shouldNotBeValid() {
        Model model = new Model();
        model.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void modelWithSpacesOnlyName_shouldNotBeValid() {
        Model model = new Model();
        model.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void modelWithValidName_shouldBeValid() {
        Model model = new Model();
        model.setName("Raspberry Pi 4");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }

    // --- TYPE TESTS -------------------------------------------------------

    @Test
    public void modelWithNullType_shouldNotBeValid() {
        Model model = new Model();
        model.setType(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "type",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur type n'a pas fonctionné");
    }

    @Test
    public void modelWithValidType_shouldBeValid() {
        Model model = new Model();
        model.setType(new Type());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(model),
                "type",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur type n'aurait pas dû fonctionner");
    }
}
