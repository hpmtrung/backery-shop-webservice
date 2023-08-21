package com.bakery.shop.service;

import com.bakery.shop.config.Constants;
import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.Authority;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.repository.AuthorityRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.security.SecurityUtils;
import com.bakery.shop.service.dto.admin.account.AdminAccountCreateDTO;
import com.bakery.shop.service.dto.admin.account.AdminAccountDTO;
import com.bakery.shop.service.dto.admin.account.AdminAccountUpdateDTO;
import com.bakery.shop.web.rest.errors.InvalidPasswordException;
import com.bakery.shop.web.rest.vm.PublicRegisterVM;
import com.bakery.shop.web.rest.vm.UserAccountVM;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/** Service class for managing accounts. */
@Service
@Transactional
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public AccountService(
        AccountRepository accountRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<Account> activateRegistration(String key) {
        log.debug("Activating account for activation key {}", key);
        return accountRepository
            .findOneByActivationKey(key)
            .map(account -> {
                // activate given account for the registration key.
                account.setActivated(true);
                account.setActivationKey(null);
                this.clearAccountCaches(account);
                log.debug("Activated account: {}", account);
                return account;
            });
    }

    public Optional<Account> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return accountRepository
            .findOneByResetKey(key)
            .filter(account -> account.getResetDate().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
            .map(account -> {
                account.setPassword(passwordEncoder.encode(newPassword));
                account.setResetKey(null);
                account.setResetDate(null);
                this.clearAccountCaches(account);
                return account;
            });
    }

    public Optional<Account> requestPasswordReset(String mail) {
        return accountRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(Account::isActivated)
            .map(account -> {
                account.setResetKey(RandomUtil.generateResetKey());
                account.setResetDate(Instant.now());
                clearAccountCaches(account);
                return account;
            });
    }

    /**
     * User registers a new account
     *
     * @param registerVM
     * @param password
     * @return
     */
    public Account registerAccount(PublicRegisterVM registerVM, String password) {
        accountRepository.findOneByEmailIgnoreCase(registerVM.getEmail()).ifPresent(this::removeNonActivatedAccount);
        Account newAccount = new Account();
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setFirstName(registerVM.getFirstName());
        newAccount.setLastName(registerVM.getLastName());
        newAccount.setPhone(registerVM.getPhone());
        newAccount.setEmail(registerVM.getEmail());
        newAccount.setAddress(registerVM.getAddress());
        newAccount.setImageUrl(Constants.DEFAULT_USER_IMAGE_URL);
        // New account is not active
        newAccount.setActivated(false);
        // New account gets registration key
        newAccount.setActivationKey(RandomUtil.generateActivationKey());
        newAccount.setLastPasswordChangeDate(LocalDateTime.now());
        // New account has USER role
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newAccount.setAuthorities(authorities);

        accountRepository.save(newAccount);
        clearAccountCaches(newAccount);
        return newAccount;
    }

    private boolean removeNonActivatedAccount(Account existingAccount) {
        if (existingAccount.isActivated()) {
            return false;
        }
        accountRepository.delete(existingAccount);
        accountRepository.flush();
        this.clearAccountCaches(existingAccount);
        return true;
    }

    /**
     * Create a new account by Administrator
     *
     * @param adminAccountDTO
     * @return
     */
    public Account createAccount(@Valid AdminAccountCreateDTO adminAccountDTO) {
        Account account = new Account();
        account.setFirstName(adminAccountDTO.getFirstName());
        account.setLastName(adminAccountDTO.getLastName());
        account.setEmail(adminAccountDTO.getEmail().toLowerCase()); // email lowercase
        account.setPhone(adminAccountDTO.getPhone());
        account.setAddress(adminAccountDTO.getAddress());
        account.setImageUrl(Constants.DEFAULT_USER_IMAGE_URL);

        if (adminAccountDTO.getLangKey() == null) {
            account.setLangKey(Constants.DEFAULT_USER_LANGUAGE); // default language
        } else {
            account.setLangKey(adminAccountDTO.getLangKey());
        }

        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        account.setPassword(encryptedPassword);
        account.setResetKey(RandomUtil.generateResetKey());
        account.setResetDate(Instant.now());
        account.setActivated(true);
        account.setLastPasswordChangeDate(LocalDateTime.now());

        Set<Authority> authorities = adminAccountDTO
            .getAuthorities()
            .stream()
            .map(authorityRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

        account.setAuthorities(authorities);
        accountRepository.save(account);
        clearAccountCaches(account);
        log.debug("Created Information for Account: {}", account);
        return account;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param adminAccountDTO user to update.
     * @return updated user.
     */
    public AdminAccountDTO updateAccountByAdmin(@Valid AdminAccountUpdateDTO adminAccountDTO) {
        Account account = accountRepository.findById(adminAccountDTO.getId()).orElseThrow();
        this.clearAccountCaches(account);
        account.setFirstName(adminAccountDTO.getFirstName());
        account.setLastName(adminAccountDTO.getLastName());
        account.setPhone(adminAccountDTO.getPhone());
        account.setLangKey(adminAccountDTO.getLangKey());
        account.setAddress(adminAccountDTO.getAddress());
        Set<Authority> managedAuthorities = account.getAuthorities();
        managedAuthorities.clear();
        adminAccountDTO
            .getAuthorities()
            .stream()
            .map(authorityRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(managedAuthorities::add);
        accountRepository.save(account);
        this.clearAccountCaches(account);
        log.debug("Changed Information for Account: {}", account);
        return new AdminAccountDTO(account);
    }

    /** Update basic information for the current user. */
    public void updateAccountByUser(UserAccountVM userAccountVM) {
        SecurityUtils
            .getCurrentAccountLogin()
            .flatMap(accountRepository::findOneByEmailIgnoreCase)
            .ifPresent(account -> {
                account.setFirstName(userAccountVM.getFirstName());
                account.setLastName(userAccountVM.getLastName());
                account.setPhone(userAccountVM.getPhone());
                account.setAddress(userAccountVM.getAddress());
                account.setLangKey(userAccountVM.getLangKey());
                this.clearAccountCaches(account);
                log.debug("Changed information for Account: {}", account);
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentAccountLogin()
            .flatMap(accountRepository::findOneByEmailIgnoreCase)
            .ifPresent(account -> {
                String currentEncryptedPassword = account.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                account.setPassword(encryptedPassword);
                account.setLastPasswordChangeDate(LocalDateTime.now());
                this.clearAccountCaches(account);
                log.debug("Changed password for Account: {}", account);
            });
    }

    @Transactional(readOnly = true)
    public Page<AdminAccountDTO> getAllManagedAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(AdminAccountDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountWithAuthoritiesById(long accountId) {
        return Optional
            .of(accountRepository.findById(accountId))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(account -> {
                Hibernate.initialize(account.getAuthorities());
                return account;
            });
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountWithAuthoritiesByEmail(String email) {
        return Optional
            .of(accountRepository.findOneByEmailIgnoreCase(email))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(account -> {
                Hibernate.initialize(account.getAuthorities());
                return account;
            });
    }

    @Transactional(readOnly = true)
    public Optional<Account> getLoginAccount() {
        return SecurityUtils.getCurrentAccountLogin().flatMap(accountRepository::findOneByEmailIgnoreCase);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getLoginAccountWithAuthorities() {
        return Optional
            .of(SecurityUtils.getCurrentAccountLogin().flatMap(accountRepository::findOneByEmailIgnoreCase))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(account -> {
                Hibernate.initialize(account.getAuthorities());
                return account;
            });
    }

    /**
     * Not activated accounts should be automatically deleted after 3 days.
     *
     * <p>This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedAccounts() {
        accountRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(account -> {
                log.debug("Deleting not activated account {}", account.getEmail());
                accountRepository.delete(account);
                this.clearAccountCaches(account);
            });
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearAccountCaches(Account account) {
        Objects.requireNonNull(cacheManager.getCache(AccountRepository.USERS_BY_EMAIL_CACHE)).evict(account.getEmail());
    }
}
