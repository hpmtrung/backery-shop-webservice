package com.bakery.shop.web.rest;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.Account;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.web.rest.vm.PublicLoginVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JWTController} REST controller.
 */
@AutoConfigureMockMvc
@IntegrationTest
class JWTControllerIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testAuthorize() throws Exception {
        Account user = new Account();
        user.setEmail("user-jwt-controller@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        accountRepository.saveAndFlush(user);

        PublicLoginVM login = new PublicLoginVM();
        login.setEmail("user-jwt-controller");
        login.setPassword("test");
        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(is(emptyString()))));
    }

    @Test
    @Transactional
    void testAuthorizeWithRememberMe() throws Exception {
        Account user = new Account();
        user.setEmail("user-jwt-controller-remember-me@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        accountRepository.saveAndFlush(user);

        PublicLoginVM login = new PublicLoginVM();
        login.setEmail("user-jwt-controller-remember-me");
        login.setPassword("test");
        login.setRememberMe(true);
        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(is(emptyString()))));
    }

    @Test
    void testAuthorizeFails() throws Exception {
        PublicLoginVM login = new PublicLoginVM();
        login.setEmail("wrong-user");
        login.setPassword("wrong password");
        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.id_token").doesNotExist())
            .andExpect(header().doesNotExist("Authorization"));
    }
}
