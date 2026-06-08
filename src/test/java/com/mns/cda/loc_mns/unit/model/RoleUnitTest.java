package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Role;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RoleUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void roleWithBlankName_shouldNotBeValid() {
        Role role = new Role();
        role.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(role),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void roleWithNullName_shouldNotBeValid() {
        Role role = new Role();
        role.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(role),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void roleWithSpacesOnlyName_shouldNotBeValid() {
        Role role = new Role();
        role.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(role),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void roleWithValidName_shouldBeValid() {
        Role role = new Role();
        role.setName("ADMIN");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(role),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }
}
