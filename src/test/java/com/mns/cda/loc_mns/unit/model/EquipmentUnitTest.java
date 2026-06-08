package com.mns.cda.loc_mns.unit.model;

import com.mns.cda.loc_mns.TestUtils;
import com.mns.cda.loc_mns.model.Equipment;
import com.mns.cda.loc_mns.model.Model;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EquipmentUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // --- NAME TESTS -------------------------------------------------------

    @Test
    public void equipmentWithBlankName_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank sur name n'a pas fonctionné");
    }

    @Test
    public void equipmentWithNullName_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(null) sur name n'a pas fonctionné");
    }

    @Test
    public void equipmentWithSpacesOnlyName_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("    ");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "NotBlank");

        Assertions.assertTrue(constraintExist, "La contrainte NotBlank(espaces) sur name n'a pas fonctionné");
    }

    @Test
    public void equipmentWithValidName_shouldBeValid() {
        Equipment equipment = new Equipment();
        equipment.setName("Oscilloscope");

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "name",
                "NotBlank");

        Assertions.assertFalse(constraintExist, "La contrainte NotBlank sur name n'aurait pas dû fonctionner");
    }

    // --- MODEL TESTS -------------------------------------------------------

    @Test
    public void equipmentWithNullModel_shouldNotBeValid() {
        Equipment equipment = new Equipment();
        equipment.setModel(null);

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "model",
                "NotNull");

        Assertions.assertTrue(constraintExist, "La contrainte NotNull sur model n'a pas fonctionné");
    }

    @Test
    public void equipmentWithValidModel_shouldBeValid() {
        Equipment equipment = new Equipment();
        equipment.setModel(new Model());

        boolean constraintExist = TestUtils.constraintViolationExist(
                validator.validate(equipment),
                "model",
                "NotNull");

        Assertions.assertFalse(constraintExist, "La contrainte NotNull sur model n'aurait pas dû fonctionner");
    }
}
