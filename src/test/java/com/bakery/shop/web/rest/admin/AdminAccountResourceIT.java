package com.bakery.shop.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.config.Constants;
import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.Authority;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.dto.admin.account.AdminAccountDTO;
import com.bakery.shop.web.rest.TestUtil;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for the {@link com.bakery.shop.web.rest.AdminAccountResource} REST controller. */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class AdminAccountResourceIT {

    private static final String TEST_USER_FNAME = "fname";
    private static final String TEST_USER_LNAME = "lname";
    private static final String TEST_USER_EMAIL_VALID = "test-valid-email@gmail.com";
    private static final Long TEST_USER_ID_VALID = 6L;
    private static final String TEST_USER_PHONE = "9999999999";
    private static final String TEST_USER_LANGKEY = Constants.DEFAULT_USER_LANGUAGE;
    private static final String TEST_USER_ADDRESS = "test-address";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MockMvc restMockMvc;

    @Test
    @Transactional
    void getAllUsers() throws Exception {
        // Initialize the database
        // accountRepository.saveAndFlush(user);

        // Get all the users
        restMockMvc
            .perform(get("/api/admin/accounts?page=2&size=2&sort=id,desc").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].firstName").exists())
            .andExpect(jsonPath("$.[*].lastName").exists())
            .andExpect(jsonPath("$.[*].email").exists())
            .andExpect(jsonPath("$.[*].phone").exists())
            .andExpect(jsonPath("$.[*].address").exists())
            .andExpect(jsonPath("$.[*].imageUrl").exists())
            .andExpect(jsonPath("$.[*].langKey").exists())
            .andExpect(jsonPath("$.[*].activated").exists());
    }

    @Test
    @Transactional
    void getUser() throws Exception {
        Account user = accountRepository.findById(TEST_USER_ID_VALID).orElseThrow();
        // Initialize the database
        // accountRepository.saveAndFlush(user);

        assertThat(cacheManager.getCache(AccountRepository.USERS_BY_EMAIL_CACHE).get(user.getEmail())).isNull();

        // Get the user
        restMockMvc
            // .perform(get("/api/admin/users/{login}", user.getLogin()))
            .perform(get("/api/admin/accounts/{id}", TEST_USER_ID_VALID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.firstName").value("Web"))
            .andExpect(jsonPath("$.lastName").value("User"))
            .andExpect(jsonPath("$.email").value("user@gmail.com"))
            .andExpect(jsonPath("$.imageUrl").value("2.jgp"))
            .andExpect(jsonPath("$.langKey").value("en"))
            .andExpect(jsonPath("$.address").isEmpty())
            .andExpect(jsonPath("$.phone").value("0323456789"))
            .andExpect(jsonPath("$.activated").value(true));
        // assertThat(cacheManager.getCache(AccountRepository.USERS_BY_EMAIL_CACHE).get(user.getEmail()))
        //     .isNotNull();
    }

    @Test
    @Transactional
    void getNonExistingUser() throws Exception {
        final int nonExistingAccountId = 0;
        restMockMvc.perform(get("/api/admin/accounts/" + nonExistingAccountId)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    // TODO: setPassword(DEFAULT_PASSWORD);
    void testCreateUser() throws Exception {
        final int databaseSizeBeforeCreate = accountRepository.findAll().size();

        // Create the User
        AdminAccountDTO adminAccountDTO = new AdminAccountDTO();
        adminAccountDTO.setFirstName(TEST_USER_FNAME);
        adminAccountDTO.setLastName(TEST_USER_LNAME);
        adminAccountDTO.setEmail(TEST_USER_EMAIL_VALID);
        adminAccountDTO.setPhone(TEST_USER_PHONE);
        adminAccountDTO.setActivated(true);
        // adminAccountDTO.setImageUrl(TEST_USER_IMAGEURL);
        adminAccountDTO.setLangKey(TEST_USER_LANGKEY);
        adminAccountDTO.setAddress(TEST_USER_ADDRESS);
        adminAccountDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restMockMvc
            .perform(
                post("/api/admin/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeCreate + 1);
            Account testUser = users.get(users.size() - 1);
            assertThat(testUser.getFirstName()).isEqualTo(adminAccountDTO.getFirstName());
            assertThat(testUser.getLastName()).isEqualTo(adminAccountDTO.getLastName());
            assertThat(testUser.getEmail()).isEqualTo(adminAccountDTO.getEmail());
            assertThat(testUser.getPhone()).isEqualTo(adminAccountDTO.getPhone());
            assertThat(testUser.getAddress()).isEqualTo(adminAccountDTO.getAddress());
            assertThat(testUser.isActivated()).isEqualTo(adminAccountDTO.isActivated());
            assertThat(testUser.getLangKey()).isEqualTo(adminAccountDTO.getLangKey());
            assertThat(testUser.getImageUrl()).isEqualTo(Constants.DEFAULT_USER_IMAGE_URL);
        });
    }

    @Test
    @Transactional
    void testCreateUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountRepository.findAll().size();

        AdminAccountDTO managedUserVM = new AdminAccountDTO();
        managedUserVM.setId(TEST_USER_ID_VALID);
        managedUserVM.setFirstName(TEST_USER_FNAME);
        managedUserVM.setLastName(TEST_USER_LNAME);
        managedUserVM.setEmail(TEST_USER_EMAIL_VALID);
        managedUserVM.setActivated(true);
        managedUserVM.setLangKey(TEST_USER_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restMockMvc
            .perform(
                post("/api/admin/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managedUserVM))
            )
            .andExpect(status().isBadRequest());

        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeCreate));
    }

    @Test
    @Transactional
    void testCreateUserWithExistingEmail() throws Exception {
        final int databaseSizeBeforeCreate = accountRepository.findAll().size();

        AdminAccountDTO managedUserVM = new AdminAccountDTO();
        managedUserVM.setFirstName(TEST_USER_FNAME);
        managedUserVM.setLastName(TEST_USER_LNAME);
        managedUserVM.setEmail(TEST_USER_EMAIL_VALID); // this email should already be used
        managedUserVM.setActivated(true);
        managedUserVM.setLangKey(TEST_USER_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restMockMvc
            .perform(
                post("/api/admin/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managedUserVM))
            )
            .andExpect(status().isBadRequest());

        assertPersistedUsers(users -> assertThat(users).hasSize(databaseSizeBeforeCreate));
    }

    @Test
    @Transactional
    void testUpdateValidUser() throws Exception {
        final int databaseSizeBeforeUpdate = accountRepository.findAll().size();

        // Update the user
        final Account updatedUser = accountRepository.findById(TEST_USER_ID_VALID).orElseThrow();

        final AdminAccountDTO managedUserVM = new AdminAccountDTO();
        managedUserVM.setId(updatedUser.getId()); // now allow change property
        managedUserVM.setFirstName("changefn");
        managedUserVM.setLastName("changeln");
        managedUserVM.setPhone("0989098961");
        managedUserVM.setEmail(updatedUser.getEmail()); // now allow change property
        managedUserVM.setActivated(updatedUser.isActivated()); // now allow change property
        managedUserVM.setImageUrl(updatedUser.getImageUrl()); // now allow change property
        managedUserVM.setLangKey("vi");
        managedUserVM.setAddress("change-address");
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restMockMvc
            .perform(
                put("/api/admin/accounts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managedUserVM))
            )
            .andExpect(status().isOk());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeUpdate);
            Account testUser = users.stream().filter(usr -> usr.getId().equals(updatedUser.getId())).findFirst().orElseThrow();
            assertThat(testUser.getFirstName()).isEqualTo(managedUserVM.getFirstName());
            assertThat(testUser.getLastName()).isEqualTo(managedUserVM.getLastName());
            assertThat(testUser.getEmail()).isEqualTo(updatedUser.getEmail());
            assertThat(testUser.getPhone()).isEqualTo(managedUserVM.getPhone());
            assertThat(testUser.getImageUrl()).isEqualTo(updatedUser.getImageUrl());
            assertThat(testUser.getAddress()).isEqualTo(managedUserVM.getAddress());
            assertThat(testUser.isActivated()).isEqualTo(updatedUser.isActivated());
        });
    }

    @Test
    @Transactional
    void testUpdateValidUserWithNotAllowProperty() throws Exception {
        final int databaseSizeBeforeUpdate = accountRepository.findAll().size();

        // Update the user
        final Account updatedUser = accountRepository.findById(TEST_USER_ID_VALID).orElseThrow();

        final AdminAccountDTO managedUserVM = new AdminAccountDTO();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setFirstName(updatedUser.getFirstName());
        managedUserVM.setLastName(updatedUser.getLastName());
        managedUserVM.setPhone(updatedUser.getPhone());
        managedUserVM.setEmail("new@mail.com"); // now allow change property
        managedUserVM.setActivated(!updatedUser.isActivated()); // now allow change property
        managedUserVM.setImageUrl("change-url"); // now allow change property
        managedUserVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restMockMvc
            .perform(
                put("/api/admin/accounts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managedUserVM))
            )
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        assertPersistedUsers(users -> {
            assertThat(users).hasSize(databaseSizeBeforeUpdate);
            Account testUser = users.stream().filter(usr -> usr.getId().equals(updatedUser.getId())).findFirst().orElseThrow();
            assertThat(testUser.getFirstName()).isEqualTo(managedUserVM.getFirstName());
            assertThat(testUser.getLastName()).isEqualTo(managedUserVM.getLastName());
            assertThat(testUser.getEmail()).isEqualTo(updatedUser.getEmail());
            assertThat(testUser.getPhone()).isEqualTo(managedUserVM.getPhone());
            assertThat(testUser.getImageUrl()).isEqualTo(updatedUser.getImageUrl());
            assertThat(testUser.getAddress()).isEqualTo(managedUserVM.getAddress());
            assertThat(testUser.isActivated()).isEqualTo(updatedUser.isActivated());
        });
    }

    @Test
    void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(Account.class);
        Account user1 = new Account();
        user1.setId(3L);
        Account user2 = new Account();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(2L);
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void testAuthorityEquals() {
        Authority authorityA = new Authority();
        assertThat(authorityA).isNotEqualTo(null).isNotEqualTo(new Object());
        assertThat(authorityA.hashCode()).isZero();
        assertThat(authorityA.toString()).isNotNull();

        Authority authorityB = new Authority();
        assertThat(authorityA).isEqualTo(authorityB);

        authorityB.setName(AuthoritiesConstants.ADMIN);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityA.setName(AuthoritiesConstants.USER);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityB.setName(AuthoritiesConstants.USER);
        assertThat(authorityA).isEqualTo(authorityB).hasSameHashCodeAs(authorityB);
    }

    private void assertPersistedUsers(Consumer<List<Account>> userAssertion) {
        userAssertion.accept(accountRepository.findAll());
    }
}
