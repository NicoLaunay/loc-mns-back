package com.mns.cda.loc_mns.unit;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.AppUser;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppUserUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validUserWithBlankName_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void validUserWithBlankSurname_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setSurname("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "surname",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur surname n'a pas fonctionné");
    }

    @Test
    public void validUserWithBlankEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur email n'a pas fonctionné");
    }

    @Test
    public void validUserWithBlankPassword_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur password n'a pas fonctionné");
    }
}
