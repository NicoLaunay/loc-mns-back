package com.mns.cda.loc_mns.unit;

import com.mns.cda.loc_mns.controller.AppUserController;
import com.mns.cda.loc_mns.mock.MockAppUserService;
import com.mns.cda.loc_mns.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppUserControllerUnitTest {

    @Test
    public void getUserByExistingId_shouldReturnCode200() {
        AppUserController userController = new AppUserController(new MockAppUserService());
        ResponseEntity<AppUser> response = userController.get(1);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
