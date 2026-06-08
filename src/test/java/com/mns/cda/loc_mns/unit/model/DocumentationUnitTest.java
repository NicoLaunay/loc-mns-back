package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Documentation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DocumentationUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- TITLE TESTS -------------------------------------------------------

    @Test
    public void documentationWithBlankTitle_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setTitle("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "title",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur title n'a pas fonctionné");
    }

    @Test
    public void documentationWithNullTitle_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setTitle(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "title",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur title n'a pas fonctionné");
    }

    @Test
    public void documentationWithSpacesOnlyTitle_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setTitle("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "title",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur title n'a pas fonctionné");
    }

    @Test
    public void documentationWithValidTitle_shouldBeValid() {
        Documentation documentation = new Documentation();
        documentation.setTitle("Manuel d'utilisation");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "title",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur title n'aurait pas dû fonctionner");
    }

    // --- URL TESTS -------------------------------------------------------

    @Test
    public void documentationWithBlankUrl_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setUrl("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "url",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur url n'a pas fonctionné");
    }

    @Test
    public void documentationWithNullUrl_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setUrl(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "url",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur url n'a pas fonctionné");
    }

    @Test
    public void documentationWithSpacesOnlyUrl_shouldNotBeValid() {
        Documentation documentation = new Documentation();
        documentation.setUrl("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "url",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur url n'a pas fonctionné");
    }

    @Test
    public void documentationWithValidUrl_shouldBeValid() {
        Documentation documentation = new Documentation();
        documentation.setUrl("https://example.com/doc.pdf");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(documentation),
                "url",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur url n'aurait pas dû fonctionner");
    }
}
