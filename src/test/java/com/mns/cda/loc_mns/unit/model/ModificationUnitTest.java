package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.AppUser;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.model.Modification;
import com.mns.cda.loc_mns.model.State;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ModificationUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- DATE TESTS -------------------------------------------------------
    @Test
    public void modificationWithNullDate_shouldNotBeValid() {
        Modification modification = new Modification();
        modification.setDate(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "date",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur date n'a pas fonctionné");
    }

    @Test
    public void modificationWithValidDate_shouldBeValid() {
        Modification modification = new Modification();
        modification.setDate(new Date());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "date",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur date n'aurait pas dû fonctionner");
    }

    // --- AUTHOR TESTS -------------------------------------------------------
    @Test
    public void modificationWithNullAuthor_shouldNotBeValid() {
        Modification modification = new Modification();
        modification.setAuthor(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "author",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur author n'a pas fonctionné");
    }

    @Test
    public void modificationWithValidAuthor_shouldBeValid() {
        Modification modification = new Modification();
        modification.setAuthor(new AppUser());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "author",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur author n'aurait pas dû fonctionner");
    }

    // --- EQUIPMENT TESTS -------------------------------------------------------
    @Test
    public void modificationWithNullEquipment_shouldNotBeValid() {
        Modification modification = new Modification();
        modification.setEquipment(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "equipment",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur equipment n'a pas fonctionné");
    }

    @Test
    public void modificationWithValidEquipment_shouldBeValid() {
        Modification modification = new Modification();
        modification.setEquipment(new Equipment());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "equipment",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur equipment n'aurait pas dû fonctionner");
    }

    // --- NEW STATE TESTS -------------------------------------------------------
    @Test
    public void modificationWithNullNewState_shouldNotBeValid() {
        Modification modification = new Modification();
        modification.setNewState(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "newState",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur newState n'a pas fonctionné");
    }

    @Test
    public void modificationWithValidNewState_shouldBeValid() {
        Modification modification = new Modification();
        modification.setNewState(new State());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(modification),
                "newState",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur newState n'aurait pas dû fonctionner");
    }
}
