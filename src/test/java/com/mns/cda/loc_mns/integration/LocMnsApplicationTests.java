package com.mns.cda.loc_mns.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// un import static permet d'utiliser une fonction sans la prefixer par le nom de sa library
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LocMnsApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = JsonMapper.builder().build(); // Utilisé dans le test de la méthode update

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void callAppUserListOpen_shouldReturnCode200() throws Exception {

        mvc.perform(get("/user/list-open"))
                .andExpect(status().isOk()); // la classe MockMvcResultMatchers (import static) permet de vérifier plein d'aspects de la réponse

    }

    @Test
    public void callAppUserListAsAnonymous_shouldReturnCode403() throws Exception {

        mvc.perform(get("/user/list"))
                .andExpect(status().isForbidden()); // la classe MockMvcResultMatchers (import static) permet de vérifier plein d'aspects de la réponse

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callAppUserListAsAdmin_shouldReturnCode200() throws Exception {

        mvc.perform(get("/user/list"))
                .andExpect(status().isOk()); // la classe MockMvcResultMatchers (import static) permet de vérifier plein d'aspects de la réponse

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void callDeleteAppUserAsAdmin_shouldReturnCode200() throws Exception {

        mvc.perform(delete("/user/1"))
                .andExpect(status().isNoContent()); // la classe MockMvcResultMatchers (import static) permet de vérifier plein d'aspects de la réponse

    }

    @Test
    @WithMockUser()
    public void callDeleteAppUserAsUser_shouldReturnCode403() throws Exception {

        mvc.perform(delete("/user/2"))
                .andExpect(status().isForbidden()); // la classe MockMvcResultMatchers (import static) permet de vérifier plein d'aspects de la réponse

    }

}
