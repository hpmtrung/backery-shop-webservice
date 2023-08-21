// package com.bakery.shop.web.rest.user;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import com.bakery.shop.IntegrationTest;
// import com.bakery.shop.config.Constants;
// import com.bakery.shop.domain.Account;
// import com.bakery.shop.repository.AccountRepository;
// import com.bakery.shop.repository.AuthorityRepository;
// import com.bakery.shop.security.AuthoritiesConstants;
// import com.bakery.shop.service.AccountService;
// import com.bakery.shop.service.FileService;
// import com.bakery.shop.service.dto.AdminAccountDTO;
// import com.bakery.shop.web.rest.TestUtil;
// import com.bakery.shop.web.rest.UserAccountResource;
// import com.bakery.shop.web.rest.WithUnauthenticatedMockUser;
// import com.bakery.shop.web.rest.vm.PublicRegisterVM;
// import com.bakery.shop.web.rest.vm.UserAccountVM;
// import com.bakery.shop.web.rest.vm.UserKeyAndPasswordVM;
// import com.bakery.shop.web.rest.vm.UserPasswordChangeVM;
// import java.time.Instant;
// import java.util.Collections;
// import java.util.HashSet;
// import java.util.Optional;
// import org.apache.commons.lang3.RandomStringUtils;
// import org.junit.Ignore;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;
//
// /** Integration tests for the {@link UserAccountResource} REST controller. */
// @AutoConfigureMockMvc
// @WithMockUser(authorities = AuthoritiesConstants.USER)
// @IntegrationTest
// @Ignore
// class UserAccountResourceIT {
//
//     private static final String TEST_USER_FNAME = "fname";
//     private static final String TEST_USER_LNAME = "lname";
//     private static final String TEST_USER_EMAIL_VALID = "test-valid-email@mail.com";
//     private static final String TEST_USER_EMAIL_INVALID = "test-invalid-email";
//     private static final String TEST_USER_PHONE = "9999999999";
//     private static final String TEST_USER_LANGKEY = Constants.DEFAULT_USER_LANGUAGE;
//     private static final String TEST_USER_ADDRESS = "test-address";
//     private static final String TEST_USER_PASSWORD = "password";
//     private static final String TEST_USER_PASSWORD_TOO_SHORT = "111";
//
//     @Autowired
//     private AccountRepository accountRepository;
//
//     @Autowired
//     private AuthorityRepository authorityRepository;
//
//     @Autowired
//     private AccountService accountService;
//
//     @Autowired
//     private PasswordEncoder passwordEncoder;
//
//     @Autowired
//     private MockMvc restMockMvc;
//
//     @MockBean
//     private FileService fileService;
//
//     @Test
//     @WithUnauthenticatedMockUser
//     void testNonAuthenticatedUser() throws Exception {
//         restMockMvc
//             .perform(get("/api/authenticate").accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isOk())
//             .andExpect(content().string(""));
//     }
//
//     @Test
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testAuthenticatedUser() throws Exception {
//         restMockMvc
//             .perform(get("/api/authenticate").accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isOk())
//             .andExpect(content().string(TEST_USER_EMAIL_VALID));
//     }
//
//     @Test
//     void testGetUnknownAccount() throws Exception {
//         restMockMvc.perform(get("/api/account").accept(MediaType.APPLICATION_PROBLEM_JSON)).andExpect(status().isInternalServerError());
//     }
//
//     @Test
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     @Transactional
//     void testGetLoginAccount() throws Exception {
//         AdminAccountDTO loginUser = new AdminAccountDTO();
//         loginUser.setFirstName(TEST_USER_FNAME);
//         loginUser.setLastName(TEST_USER_LNAME);
//         loginUser.setEmail(TEST_USER_EMAIL_VALID);
//         loginUser.setPhone(TEST_USER_PHONE);
//         loginUser.setLangKey(TEST_USER_LANGKEY);
//         loginUser.setAddress(TEST_USER_ADDRESS);
//
//         // Create a new account from Admin Authority
//         accountService.createAccount(loginUser);
//
//         restMockMvc
//             .perform(get("/api/account").accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.firstName").value(loginUser.getFirstName()))
//             .andExpect(jsonPath("$.lastName").value(loginUser.getLastName()))
//             .andExpect(jsonPath("$.imageUrl").value(Constants.DEFAULT_USER_IMAGE_URL))
//             .andExpect(jsonPath("$.email").value(loginUser.getEmail()))
//             .andExpect(jsonPath("$.phone").value(loginUser.getPhone()))
//             .andExpect(jsonPath("$.langKey").value(loginUser.getLangKey()))
//             .andExpect(jsonPath("$.address").value(loginUser.getAddress()))
//             .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.USER));
//     }
//
//     @Test
//     @Transactional
//     void testRegisterValid() throws Exception {
//         PublicRegisterVM validUser = new PublicRegisterVM();
//         validUser.setFirstName(TEST_USER_FNAME);
//         validUser.setLastName(TEST_USER_LNAME);
//         validUser.setEmail(TEST_USER_EMAIL_VALID);
//         validUser.setPhone(TEST_USER_PHONE);
//         validUser.setPassword(TEST_USER_PASSWORD);
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID)).isEmpty();
//
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validUser)))
//             .andExpect(status().isCreated());
//
//         Account account = accountRepository.findOneByEmailIgnoreCase(validUser.getEmail()).orElse(null);
//         assertThat(account).isNotNull();
//         assertThat(account.getFirstName()).isEqualTo(validUser.getFirstName());
//         assertThat(account.getLastName()).isEqualTo(validUser.getLastName());
//         assertThat(account.getPhone()).isEqualTo(validUser.getPhone());
//         assertThat(account.getEmail()).isEqualTo(validUser.getEmail());
//         assertThat(account.getAddress()).isEqualTo(validUser.getAddress());
//         assertThat(account.isActivated()).isFalse();
//         assertThat(account.getImageUrl()).isEqualTo(Constants.DEFAULT_USER_IMAGE_URL);
//         assertThat(account.getAuthorities())
//             .hasSize(1)
//             .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow(NullPointerException::new));
//     }
//
//     @Test
//     @Transactional
//     void testRegisterInvalidEmail() throws Exception {
//         PublicRegisterVM invalidUser = new PublicRegisterVM();
//         invalidUser.setFirstName(TEST_USER_FNAME);
//         invalidUser.setLastName(TEST_USER_LNAME);
//         invalidUser.setEmail(TEST_USER_EMAIL_INVALID);
//         invalidUser.setPhone(TEST_USER_PHONE);
//         invalidUser.setPassword(TEST_USER_PASSWORD);
//
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
//             .andExpect(status().isBadRequest());
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID)).isEmpty();
//     }
//
//     @Test
//     @Transactional
//     void testRegisterInvalidPhone() throws Exception {
//         PublicRegisterVM invalidUser = new PublicRegisterVM();
//         invalidUser.setFirstName(TEST_USER_FNAME);
//         invalidUser.setLastName(TEST_USER_LNAME);
//         invalidUser.setEmail(TEST_USER_EMAIL_INVALID);
//         invalidUser.setPhone("invalid-phone");
//         invalidUser.setPassword(TEST_USER_PASSWORD);
//
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
//             .andExpect(status().isBadRequest());
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_INVALID)).isEmpty();
//     }
//
//     @Test
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     @Transactional
//     void testRegisterInvalidPassword() throws Exception {
//         PublicRegisterVM invalidUser = new PublicRegisterVM();
//         invalidUser.setFirstName(TEST_USER_FNAME);
//         invalidUser.setLastName(TEST_USER_LNAME);
//         invalidUser.setEmail(TEST_USER_EMAIL_VALID);
//         invalidUser.setPhone(TEST_USER_PHONE);
//         invalidUser.setPassword(TEST_USER_PASSWORD_TOO_SHORT);
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
//             .andExpect(status().isBadRequest());
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID)).isEmpty();
//     }
//
//     @Test
//     @Transactional
//     void testRegisterDuplicateEmail() throws Exception {
//         // First user
//         PublicRegisterVM firstUser = new PublicRegisterVM();
//         firstUser.setFirstName(TEST_USER_FNAME);
//         firstUser.setLastName(TEST_USER_LNAME);
//         firstUser.setEmail(TEST_USER_EMAIL_VALID);
//         firstUser.setPhone(TEST_USER_PHONE);
//         firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
//         firstUser.setPassword(TEST_USER_PASSWORD);
//         // Register first user
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(firstUser)))
//             .andExpect(status().isCreated());
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID)).isPresent();
//
//         // Duplicate email
//         PublicRegisterVM secondUser = new PublicRegisterVM();
//         secondUser.setFirstName(firstUser.getFirstName());
//         secondUser.setLastName(firstUser.getLastName());
//         secondUser.setEmail(firstUser.getEmail());
//         secondUser.setPhone(firstUser.getPhone());
//         secondUser.setPassword(firstUser.getPassword());
//         secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));
//
//         // Register second (non activated) user
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(secondUser)))
//             .andExpect(status().isBadRequest());
//
//         // Duplicate email - with uppercase email address
//         PublicRegisterVM userWithUpperCaseEmail = new PublicRegisterVM();
//         userWithUpperCaseEmail.setFirstName(firstUser.getFirstName());
//         userWithUpperCaseEmail.setLastName(firstUser.getLastName());
//         userWithUpperCaseEmail.setEmail(TEST_USER_EMAIL_VALID.toUpperCase());
//         userWithUpperCaseEmail.setPhone(firstUser.getPhone());
//         userWithUpperCaseEmail.setPassword(TEST_USER_PASSWORD);
//         userWithUpperCaseEmail.setAuthorities(new HashSet<>(firstUser.getAuthorities()));
//
//         // Register third (not activated) user
//         restMockMvc
//             .perform(
//                 post("/api/register")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(userWithUpperCaseEmail))
//             )
//             .andExpect(status().isBadRequest());
//     }
//
//     @Test
//     @Transactional
//     void testRegisterAdminIsIgnored() throws Exception {
//         PublicRegisterVM user = new PublicRegisterVM();
//         user.setFirstName(TEST_USER_FNAME);
//         user.setLastName(TEST_USER_LNAME);
//         user.setEmail(TEST_USER_EMAIL_VALID);
//         user.setPhone(TEST_USER_PHONE);
//         user.setPassword(TEST_USER_PASSWORD);
//         // Set authority to Admin but system ignores and changes to User
//         user.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));
//
//         restMockMvc
//             .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(user)))
//             .andExpect(status().isCreated());
//
//         Optional<Account> account = accountRepository.findOneWithAuthoritiesByEmailIgnoreCase(TEST_USER_EMAIL_VALID);
//         assertThat(account).isPresent();
//         assertThat(account.get().getAuthorities())
//             .hasSize(1)
//             .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow());
//     }
//
//     @Test
//     @Transactional
//     void testActivateAccount() throws Exception {
//         final String activationKey = "some activation key";
//
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setActivated(false);
//         account.setActivationKey(activationKey);
//
//         accountRepository.saveAndFlush(account);
//
//         restMockMvc.perform(get("/api/activate").param("key", activationKey)).andExpect(status().isOk());
//
//         account = accountRepository.findOneByEmailIgnoreCase(account.getEmail()).orElse(null);
//         Assertions.assertNotNull(account);
//         assertThat(account.isActivated()).isTrue();
//     }
//
//     @Test
//     @Transactional
//     void testActivateAccountWithWrongKey() throws Exception {
//         restMockMvc.perform(get("/api/activate").param("key", "wrongActivationKey")).andExpect(status().isInternalServerError());
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testUpdateAccountWithNoImageUpload() throws Exception {
//         final MockMultipartFile mockEmptyUserImageFile = new MockMultipartFile(
//             "userImage",
//             "test.png",
//             MediaType.TEXT_PLAIN_VALUE,
//             "".getBytes()
//         );
//
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//         account.setImageUrl(Constants.DEFAULT_USER_IMAGE_URL);
//         account.setLangKey(Constants.DEFAULT_USER_LANGUAGE);
//         account.setAddress(TEST_USER_ADDRESS);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setActivated(true);
//         account.setAuthorities(Collections.singleton(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow()));
//
//         account = accountRepository.saveAndFlush(account);
//
//         UserAccountVM userAccountVM = new UserAccountVM();
//         userAccountVM.setFirstName("fnchange");
//         userAccountVM.setLastName("lnchange");
//         userAccountVM.setEmail(account.getEmail());
//         userAccountVM.setPhone("9999999998");
//         userAccountVM.setImageUrl(account.getImageUrl());
//         userAccountVM.setAddress("test-address-change");
//         userAccountVM.setLangKey("en");
//
//         restMockMvc
//             .perform(
//                 MockMvcRequestBuilders
//                     .multipart("/api/account")
//                     .file(mockEmptyUserImageFile)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(userAccountVM))
//             )
//             .andExpect(status().isOk());
//
//         Account updatedUser = accountRepository.findOneWithAuthoritiesByEmailIgnoreCase(account.getEmail()).orElse(null);
//         assertThat(updatedUser).isNotNull();
//         assertThat(updatedUser.getFirstName()).isEqualTo(userAccountVM.getFirstName());
//         assertThat(updatedUser.getLastName()).isEqualTo(userAccountVM.getLastName());
//         assertThat(updatedUser.getEmail()).isEqualTo(userAccountVM.getEmail());
//         assertThat(updatedUser.getPhone()).isEqualTo(userAccountVM.getPhone());
//         assertThat(updatedUser.getLangKey()).isEqualTo(userAccountVM.getLangKey());
//         assertThat(updatedUser.getAddress()).isEqualTo(userAccountVM.getAddress());
//         // Assert that account image doesn't change
//         assertThat(updatedUser.getImageUrl()).isEqualTo(account.getImageUrl());
//         assertThat(updatedUser.isActivated()).isTrue();
//         assertThat(updatedUser.getAuthorities())
//             .hasSize(1)
//             .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow(NullPointerException::new));
//         verify(fileService, never()).saveUserImage(any(MultipartFile.class), any(String.class));
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testUpdateAccountWithImageUpload() throws Exception {
//         ClassPathResource imageResource = new ClassPathResource("user-image-test.png", getClass());
//         assertThat(imageResource).isNotNull();
//
//         final MockMultipartFile mockEmptyUserImageFile = new MockMultipartFile(
//             "userImage",
//             imageResource.getFilename(),
//             MediaType.TEXT_PLAIN_VALUE,
//             imageResource.getInputStream().readAllBytes()
//         );
//
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//         account.setImageUrl(Constants.DEFAULT_USER_IMAGE_URL);
//         account.setLangKey(Constants.DEFAULT_USER_LANGUAGE);
//         account.setAddress(TEST_USER_ADDRESS);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setActivated(true);
//         account.setAuthorities(Collections.singleton(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow()));
//
//         accountRepository.saveAndFlush(account);
//
//         UserAccountVM userAccountVM = new UserAccountVM();
//         userAccountVM.setFirstName("fnchange");
//         userAccountVM.setLastName("lnchange");
//         userAccountVM.setEmail(account.getEmail());
//         userAccountVM.setPhone("9999999998");
//         userAccountVM.setImageUrl(account.getImageUrl());
//         userAccountVM.setAddress("test-address-change");
//         userAccountVM.setLangKey("en");
//
//         restMockMvc
//             .perform(
//                 MockMvcRequestBuilders
//                     .multipart("/api/account")
//                     .file(mockEmptyUserImageFile)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(userAccountVM))
//             )
//             .andExpect(status().isOk());
//
//         Account updatedUser = accountRepository.findOneWithAuthoritiesByEmailIgnoreCase(account.getEmail()).orElse(null);
//         assertThat(updatedUser).isNotNull();
//         assertThat(updatedUser.getFirstName()).isEqualTo(userAccountVM.getFirstName());
//         assertThat(updatedUser.getLastName()).isEqualTo(userAccountVM.getLastName());
//         assertThat(updatedUser.getEmail()).isEqualTo(userAccountVM.getEmail());
//         assertThat(updatedUser.getPhone()).isEqualTo(userAccountVM.getPhone());
//         assertThat(updatedUser.getLangKey()).isEqualTo(userAccountVM.getLangKey());
//         assertThat(updatedUser.getAddress()).isEqualTo(userAccountVM.getAddress());
//         // Assert that account image doesn't change
//         final String updateImageUrl = FileService.getUserImageFileNameFromAccount(updatedUser, ".png");
//         assertThat(updatedUser.getImageUrl()).isEqualTo(updateImageUrl);
//         assertThat(updatedUser.isActivated()).isTrue();
//         assertThat(updatedUser.getAuthorities())
//             .hasSize(1)
//             .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).orElseThrow(NullPointerException::new));
//
//         verify(fileService, times(1)).saveUserImage(mockEmptyUserImageFile, updateImageUrl);
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_INVALID)
//     void testSaveInvalidEmail() throws Exception {
//         UserAccountVM userAccountVM = new UserAccountVM();
//         userAccountVM.setFirstName(TEST_USER_FNAME);
//         userAccountVM.setLastName(TEST_USER_LNAME);
//         userAccountVM.setEmail(TEST_USER_EMAIL_INVALID);
//         userAccountVM.setPhone(TEST_USER_PHONE);
//         userAccountVM.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
//
//         restMockMvc
//             .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccountVM)))
//             .andExpect(status().isBadRequest());
//
//         assertThat(accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_INVALID)).isNotPresent();
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testChangePasswordWrongExistingPassword() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         final String currentPassword = RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH);
//         account.setPassword(passwordEncoder.encode(currentPassword));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         final String wrongPassword = "1" + currentPassword;
//         restMockMvc
//             .perform(
//                 post("/api/account/change-password")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(new UserPasswordChangeVM(wrongPassword, "new password")))
//             )
//             .andExpect(status().isBadRequest());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(passwordEncoder.matches("new password", updatedUser.getPassword())).isFalse();
//         assertThat(passwordEncoder.matches(currentPassword, updatedUser.getPassword())).isTrue();
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testChangePassword() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         String currentPassword = RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH);
//         account.setPassword(passwordEncoder.encode(currentPassword));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         restMockMvc
//             .perform(
//                 post("/api/account/change-password")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(new UserPasswordChangeVM(currentPassword, "new password")))
//             )
//             .andExpect(status().isOk());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(passwordEncoder.matches("new password", updatedUser.getPassword())).isTrue();
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testChangePasswordTooSmall() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         String currentPassword = RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH);
//         account.setPassword(passwordEncoder.encode(currentPassword));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         String newPassword = RandomStringUtils.random(Constants.USER_PASSWORD_MIN_LENGTH - 1);
//
//         restMockMvc
//             .perform(
//                 post("/api/account/change-password")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(new UserPasswordChangeVM(currentPassword, newPassword)))
//             )
//             .andExpect(status().isBadRequest());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(updatedUser.getPassword()).isEqualTo(account.getPassword());
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testChangePasswordTooLong() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         String currentPassword = RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH);
//         account.setPassword(passwordEncoder.encode(currentPassword));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         String newPassword = RandomStringUtils.random(Constants.USER_PASSWORD_MAX_LENGTH + 1);
//
//         restMockMvc
//             .perform(
//                 post("/api/account/change-password")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(new UserPasswordChangeVM(currentPassword, newPassword)))
//             )
//             .andExpect(status().isBadRequest());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(updatedUser.getPassword()).isEqualTo(account.getPassword());
//     }
//
//     @Test
//     @Transactional
//     @WithMockUser(TEST_USER_EMAIL_VALID)
//     void testChangePasswordEmpty() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         String currentPassword = RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH);
//         account.setPassword(passwordEncoder.encode(currentPassword));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         restMockMvc
//             .perform(
//                 post("/api/account/change-password")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(new UserPasswordChangeVM(currentPassword, "")))
//             )
//             .andExpect(status().isBadRequest());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(TEST_USER_EMAIL_VALID).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(updatedUser.getPassword()).isEqualTo(account.getPassword());
//     }
//
//     @Test
//     @Transactional
//     void testRequestPasswordReset() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setActivated(true);
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//         accountRepository.saveAndFlush(account);
//
//         restMockMvc.perform(post("/api/account/reset-password/init").content("password-reset@example.com")).andExpect(status().isOk());
//     }
//
//     @Test
//     @Transactional
//     void testRequestPasswordResetUpperCaseEmail() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setActivated(true);
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//
//         accountRepository.saveAndFlush(account);
//
//         restMockMvc
//             .perform(post("/api/account/reset-password/init").content(TEST_USER_EMAIL_VALID.toUpperCase()))
//             .andExpect(status().isOk());
//     }
//
//     @Test
//     void testRequestPasswordResetWrongEmail() throws Exception {
//         restMockMvc
//             .perform(post("/api/account/reset-password/init").content("password-reset-wrong-email@example.com"))
//             .andExpect(status().isOk());
//     }
//
//     @Test
//     @Transactional
//     void testFinishPasswordReset() throws Exception {
//         Account account = new Account();
//         account.setFirstName(TEST_USER_FNAME);
//         account.setLastName(TEST_USER_LNAME);
//         account.setPassword(RandomStringUtils.random(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setEmail(TEST_USER_EMAIL_VALID);
//         account.setPhone(TEST_USER_PHONE);
//         account.setResetDate(Instant.now().plusSeconds(Constants.SPRING_SECURITY_PASSWORD_HASH_LENGTH));
//         account.setResetKey("reset key");
//         accountRepository.saveAndFlush(account);
//
//         UserKeyAndPasswordVM keyAndPassword = new UserKeyAndPasswordVM();
//         keyAndPassword.setKey(account.getResetKey());
//         keyAndPassword.setNewPassword("new password");
//
//         restMockMvc
//             .perform(
//                 post("/api/account/reset-password/finish")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(keyAndPassword))
//             )
//             .andExpect(status().isOk());
//
//         Account updatedUser = accountRepository.findOneByEmailIgnoreCase(account.getEmail()).orElse(null);
//         Assertions.assertNotNull(updatedUser);
//         assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword())).isTrue();
//     }
//
//     @Test
//     @Transactional
//     void testFinishPasswordResetWrongKey() throws Exception {
//         UserKeyAndPasswordVM keyAndPassword = new UserKeyAndPasswordVM();
//         keyAndPassword.setKey("wrong reset key");
//         keyAndPassword.setNewPassword("new password");
//
//         restMockMvc
//             .perform(
//                 post("/api/account/reset-password/finish")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(keyAndPassword))
//             )
//             .andExpect(status().isInternalServerError());
//     }
//
//     @Test
//     void testUserOrderView() {}
// }
