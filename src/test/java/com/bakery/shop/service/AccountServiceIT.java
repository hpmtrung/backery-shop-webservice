package com.bakery.shop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.Account;
import com.bakery.shop.repository.AccountRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Integration tests for {@link AccountService}.
 */
@IntegrationTest
@Transactional
class AccountServiceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuditingHandler auditingHandler;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    private Account account;

    @BeforeEach
    public void init() {
        account = new Account();
        // account.setLogin(DEFAULT_LOGIN);
        account.setPassword(RandomStringUtils.random(60));
        account.setActivated(true);
        account.setEmail(DEFAULT_EMAIL);
        account.setFirstName(DEFAULT_FIRSTNAME);
        account.setLastName(DEFAULT_LASTNAME);
        // account.setImageUrl(DEFAULT_IMAGEURL);
        // account.setLangKey(DEFAULT_LANGKEY);

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    void assertThatUserMustExistToResetPassword() {
        accountRepository.saveAndFlush(account);
        Optional<Account> maybeUser = accountService.requestPasswordReset("invalid.login@localhost");
        assertThat(maybeUser).isNotPresent();

        maybeUser = accountService.requestPasswordReset(account.getEmail());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getEmail()).isEqualTo(account.getEmail());
        assertThat(maybeUser.orElse(null).getResetDate()).isNotNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNotNull();
    }

    @Test
    @Transactional
    void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        account.setActivated(false);
        accountRepository.saveAndFlush(account);

        // Optional<Account> maybeUser = userService.requestPasswordReset(account.getLogin());
        Optional<Account> maybeUser = accountService.requestPasswordReset(account.getEmail());
        assertThat(maybeUser).isNotPresent();
        accountRepository.delete(account);
    }

    @Test
    @Transactional
    void assertThatResetKeyMustNotBeOlderThan24Hours() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        account.setActivated(true);
        account.setResetDate(daysAgo);
        account.setResetKey(resetKey);
        accountRepository.saveAndFlush(account);

        Optional<Account> maybeUser = accountService.completePasswordReset("johndoe2", account.getResetKey());
        assertThat(maybeUser).isNotPresent();
        accountRepository.delete(account);
    }

    @Test
    @Transactional
    void assertThatResetKeyMustBeValid() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        account.setActivated(true);
        account.setResetDate(daysAgo);
        account.setResetKey("1234");
        accountRepository.saveAndFlush(account);

        Optional<Account> maybeUser = accountService.completePasswordReset("johndoe2", account.getResetKey());
        assertThat(maybeUser).isNotPresent();
        accountRepository.delete(account);
    }

    @Test
    @Transactional
    void assertThatUserCanResetPassword() {
        String oldPassword = account.getPassword();
        Instant daysAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        account.setActivated(true);
        account.setResetDate(daysAgo);
        account.setResetKey(resetKey);
        accountRepository.saveAndFlush(account);

        Optional<Account> maybeUser = accountService.completePasswordReset("johndoe2", account.getResetKey());
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getResetDate()).isNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNull();
        assertThat(maybeUser.orElse(null).getPassword()).isNotEqualTo(oldPassword);

        accountRepository.delete(account);
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNotNullActivationKeyCreatedBefore3DaysAreDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        account.setActivated(false);
        account.setActivationKey(RandomStringUtils.random(20));
        Account dbUser = accountRepository.saveAndFlush(account);
        // dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        accountRepository.saveAndFlush(account);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<Account> users = accountRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(threeDaysAgo);
        assertThat(users).isNotEmpty();
        accountService.removeNotActivatedAccounts();
        users = accountRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(threeDaysAgo);
        assertThat(users).isEmpty();
    }

    @Test
    @Transactional
    void assertThatNotActivatedUsersWithNullActivationKeyCreatedBefore3DaysAreNotDeleted() {
        Instant now = Instant.now();
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minus(4, ChronoUnit.DAYS)));
        account.setActivated(false);
        Account dbUser = accountRepository.saveAndFlush(account);
        // dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        accountRepository.saveAndFlush(account);
        Instant threeDaysAgo = now.minus(3, ChronoUnit.DAYS);
        List<Account> users = accountRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(threeDaysAgo);
        assertThat(users).isEmpty();
        accountService.removeNotActivatedAccounts();
        Optional<Account> maybeDbUser = accountRepository.findById(dbUser.getId());
        assertThat(maybeDbUser).contains(dbUser);
    }
}
