package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Accreditation;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Role;
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

    // --- NAME TESTS -------------------------------------------------------
    @Test
    public void userWithBlankName_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void userWithNullName_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void userWithSpacesOnlyName_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void userWithValidName_shouldBeValid() {
        AppUser user = new AppUser();
        user.setName("John");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }

    // --- SURNAME TESTS -------------------------------------------------------
    @Test
    public void userWithBlankSurname_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setSurname("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "surname",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur surname n'a pas fonctionné");
    }

    @Test
    public void userWithNullSurname_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setSurname(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "surname",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur surname n'a pas fonctionné");
    }

    @Test
    public void userWithSpacesOnlySurname_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setSurname("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "surname",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur surname n'a pas fonctionné");
    }

    @Test
    public void userWithValidSurname_shouldBeValid() {
        AppUser user = new AppUser();
        user.setSurname("Doe");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "surname",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur surname n'aurait pas dû fonctionner");
    }

    // --- EMAIL TESTS -------------------------------------------------------
    @Test
    public void userWithBlankEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur email n'a pas fonctionné");
    }

    @Test
    public void userWithNullEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur email n'a pas fonctionné");
    }

    @Test
    public void userWithSpacesOnlyEmail_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur email n'a pas fonctionné");
    }

    @Test
    public void userWithEmailWithoutAtSign_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("john.com");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "Email");

        Assertions.assertTrue(constraintExist, "La contrainte Email sur email n'a pas fonctionné");
    }

    @Test
    public void userWithEmailWithoutLocalPart_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setEmail("@email.com");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "Email");

        Assertions.assertTrue(constraintExist, "La contrainte Email sur email n'a pas fonctionné pour une local part vide");
    }

    @Test
    public void userWithValidEmail_shouldBeValid() {
        AppUser user = new AppUser();
        user.setEmail("john.doe@email.com");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "email",
                "Email");

        Assertions.assertFalse(constraintExist, "La contrainte Email sur email n'aurait pas dû fonctionner");
    }

    // --- PASSWORD TESTS -------------------------------------------------------
    @Test
    public void userWithBlankPassword_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur password n'a pas fonctionné");
    }

    @Test
    public void userWithNullPassword_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur password n'a pas fonctionné");
    }

    @Test
    public void userWithTooShortPassword_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("Ab1!");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Size");

        Assertions.assertTrue(constraintExist, "La contrainte Size sur password n'a pas fonctionné");
    }

    @Test
    public void userWithTooLongPassword_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("Abcdefgh1!Abcdefgh1!X");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Size");

        Assertions.assertTrue(constraintExist, "La contrainte Size sur password n'a pas fonctionné");
    }

    @Test
    public void userWithPasswordWithoutUppercase_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("password49!");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Pattern");

        Assertions.assertTrue(constraintExist, "La contrainte Pattern(sans majuscule) sur password n'a pas fonctionné");
    }

    @Test
    public void userWithPasswordWithoutLowercase_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("PASSWORD49!");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Pattern");

        Assertions.assertTrue(constraintExist, "La contrainte Pattern(sans minuscule) sur password n'a pas fonctionné");
    }

    @Test
    public void userWithPasswordWithoutNumber_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("Password!");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Pattern");

        Assertions.assertTrue(constraintExist, "La contrainte Pattern(sans chiffre) sur password n'a pas fonctionné");
    }

    @Test
    public void userWithPasswordWithoutSpecialChar_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setPassword("Password49");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Pattern");

        Assertions.assertTrue(constraintExist, "La contrainte Pattern(sans caractère spécial) sur password n'a pas fonctionné");
    }

    @Test
    public void userWithValidPassword_shouldBeValid() {
        AppUser user = new AppUser();
        user.setPassword("Password49!");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "password",
                "Pattern");

        Assertions.assertFalse(constraintExist, "La contrainte Pattern sur password n'aurait pas dû fonctionner");
    }

    // --- ACCREDITATION TESTS -------------------------------------------------------
    @Test
    public void userWithNullAccreditation_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setAccreditation(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "accreditation",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur accreditation n'a pas fonctionné");
    }

    @Test
    public void userWithValidAccreditation_shouldBeValid() {
        AppUser user = new AppUser();
        user.setAccreditation(new Accreditation());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "accreditation",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur accreditation n'aurait pas dû fonctionner");
    }

    // --- ROLE TESTS -------------------------------------------------------
    @Test
    public void userWithNullRole_shouldNotBeValid() {
        AppUser user = new AppUser();
        user.setRole(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "role",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur role n'a pas fonctionné");
    }

    @Test
    public void userWithValidRole_shouldBeValid() {
        AppUser user = new AppUser();
        user.setRole(new Role());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(user),
                "role",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur role n'aurait pas dû fonctionner");
    }
}
