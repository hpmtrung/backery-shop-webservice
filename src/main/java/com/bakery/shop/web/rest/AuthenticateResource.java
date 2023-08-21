package com.bakery.shop.web.rest;

import com.bakery.shop.domain.Account;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.repository.product.ProductCategoryRepository;
import com.bakery.shop.security.jwt.JWTFilter;
import com.bakery.shop.security.jwt.TokenProvider;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.MailService;
import com.bakery.shop.web.rest.errors.EmailAlreadyUsedException;
import com.bakery.shop.web.rest.vm.PublicLoginVM;
import com.bakery.shop.web.rest.vm.PublicRegisterVM;
import com.bakery.shop.web.rest.vm.UserKeyAndPasswordVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticateResource {

    private final Logger log = LoggerFactory.getLogger(AuthenticateResource.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MailService mailService;

    public AuthenticateResource(
        AccountService accountService,
        AccountRepository accountRepository,
        MailService mailService,
        ProductCategoryRepository categoryRepository,
        TokenProvider tokenProvider,
        AuthenticationManagerBuilder authenticationManagerBuilder
    ) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.mailService = mailService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody PublicLoginVM publicLoginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            publicLoginVM.getEmail(),
            publicLoginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account user = accountRepository.findOneByEmailIgnoreCase(authentication.getName()).get();
        String jwt = tokenProvider.createToken(authentication, publicLoginVM.isRememberMe(), user.getLastPasswordChangeDate());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * {@code POST /register} : register the user.
     *
     * @param registerVM account register View Model.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody PublicRegisterVM registerVM) {
        Account existingAccountWithSameEmail = accountRepository.findOneByEmailIgnoreCase(registerVM.getEmail()).orElse(null);
        if (existingAccountWithSameEmail != null && existingAccountWithSameEmail.isActivated()) {
            throw new EmailAlreadyUsedException();
        }

        Account account = accountService.registerAccount(registerVM, registerVM.getPassword());
        // Send activation key to register email
        mailService.sendActivationEmail(account);
    }

    /**
     * {@code GET /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam("key") String key) {
        Optional<Account> account = accountService.activateRegistration(key);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user was found for this registration key");
        }
    }

    /**
     * {@code POST /account/reset-password/init} : Send an email to reset the password of the user
     *
     * @param email the email of the user
     */
    @PostMapping("/reset-password/init")
    public void initPasswordReset(@RequestBody String email) {
        Optional<Account> account = accountService.requestPasswordReset(email);
        if (account.isPresent()) {
            mailService.sendPasswordResetMail(account.get());
        } else {
            log.warn("Password reset requested for non-existing email");
        }
    }

    /**
     * {@code POST /reset-password/finish} : Finish to reset the password of the user.
     *
     * @param userKeyAndPasswordVM the generated key and the new password.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if no user is found for the given
     *     reset key
     */
    @PostMapping("/reset-password/finish")
    public void finishPasswordReset(@RequestBody UserKeyAndPasswordVM userKeyAndPasswordVM) {
        Optional<Account> account = accountService.completePasswordReset(
            userKeyAndPasswordVM.getNewPassword(),
            userKeyAndPasswordVM.getKey()
        );
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user was found for this reset key");
        }
    }

    /** Object to return as body in JWT Authentication. */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

    public static class AccountResourceException extends RuntimeException {

        private static final long serialVersionUID = 101669756024626374L;

        public AccountResourceException(String message) {
            super(message);
        }
    }
}
