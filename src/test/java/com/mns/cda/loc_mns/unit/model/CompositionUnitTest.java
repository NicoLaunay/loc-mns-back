package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Composition;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CompositionUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- AMOUNT TESTS -------------------------------------------------------

    @Test
    public void compositionWithAmountZero_shouldNotBeValid() {
        Composition composition = new Composition();
        composition.setAmount(0);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(composition),
                "amount",
                "Min");

        Assertions.assertTrue(constraintExist, "La contrainte Min sur amount n'a pas fonctionné");
    }

    @Test
    public void compositionWithNegativeAmount_shouldNotBeValid() {
        Composition composition = new Composition();
        composition.setAmount(-1);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(composition),
                "amount",
                "Min");

        Assertions.assertTrue(constraintExist, "La contrainte Min sur amount n'a pas fonctionné pour une valeur négative");
    }

    @Test
    public void compositionWithAmountOne_shouldBeValid() {
        Composition composition = new Composition();
        composition.setAmount(1);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(composition),
                "amount",
                "Min");

        Assertions.assertFalse(constraintExist, "La contrainte Min sur amount n'aurait pas dû fonctionner pour la valeur 1");
    }

    @Test
    public void compositionWithHighAmount_shouldBeValid() {
        Composition composition = new Composition();
        composition.setAmount(9999);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(composition),
                "amount",
                "Min");

        Assertions.assertFalse(constraintExist, "La contrainte Min sur amount n'aurait pas dû fonctionner pour une grande valeur");
    }
}
