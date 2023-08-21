package com.bakery.shop.web.rest;

import com.bakery.shop.domain.Account;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.MailService;
import com.bakery.shop.service.dto.admin.account.AdminAccountCreateDTO;
import com.bakery.shop.service.dto.admin.account.AdminAccountDTO;
import com.bakery.shop.service.dto.admin.account.AdminAccountUpdateDTO;
import com.bakery.shop.web.rest.errors.AccountIdNotFoundException;
import com.bakery.shop.web.rest.errors.BadRequestException;
import com.bakery.shop.web.rest.errors.EmailAlreadyUsedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminAccountResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = List.of(
        "id",
        "firstName",
        "lastName",
        "email",
        "phone",
        "address",
        "activated",
        "langKey"
    );

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MailService mailService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AdminAccountResource(AccountService accountService, AccountRepository accountRepository, MailService mailService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.mailService = mailService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<AdminAccountDTO>> getAllUsers(@ParameterObject @PageableDefault Pageable pageable) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            throw new BadRequestException("Params of pagination contains unallowed properties", "invalidPagination");
        }

        final Page<AdminAccountDTO> page = accountService.getAllManagedAccounts(pageable);
        final String userImagePrefix = ResourceUtil.getUserImageURLPrefix();
        page.map(adminAccountDTO -> adminAccountDTO.setImageUrl(userImagePrefix + adminAccountDTO.getImageUrl()));
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminAccountDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User id : {}", id);
        final String userImagePrefix = ResourceUtil.getUserImageURLPrefix();
        Account account = accountService.getAccountWithAuthoritiesById(id).orElseThrow(AccountIdNotFoundException::new);

        AdminAccountDTO adminAccountDTO = new AdminAccountDTO(account);
        adminAccountDTO.setImageUrl(userImagePrefix + adminAccountDTO.getImageUrl());
        return ResponseEntity.ok(adminAccountDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminAccountDTO> createUser(@RequestBody @Valid AdminAccountCreateDTO adminAccountCreateDTO)
        throws URISyntaxException {
        log.debug("REST request to save User : {}", adminAccountCreateDTO);

        if (accountRepository.findOneByEmailIgnoreCase(adminAccountCreateDTO.getEmail().toLowerCase()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        Account newUser = accountService.createAccount(adminAccountCreateDTO);
        mailService.sendCreationEmail(newUser);

        AdminAccountDTO adminAccountDTO = new AdminAccountDTO(newUser);

        final String userImagePrefix = ResourceUtil.getUserImageURLPrefix();
        adminAccountDTO.setImageUrl(userImagePrefix + newUser.getImageUrl());
        return ResponseEntity.created(new URI("/api/admin/accounts/" + adminAccountDTO.getId())).body(adminAccountDTO);
    }

    @PutMapping
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminAccountDTO> updateUser(@RequestBody @Valid AdminAccountUpdateDTO adminAccountUpdateDTO) {
        log.debug("REST request to update User : {}", adminAccountUpdateDTO);
        Account account = accountRepository.findById(adminAccountUpdateDTO.getId()).orElseThrow(AccountIdNotFoundException::new);

        final String userImagePrefix = ResourceUtil.getUserImageURLPrefix();

        AdminAccountDTO updatedUser = accountService.updateAccountByAdmin(adminAccountUpdateDTO);
        updatedUser.setImageUrl(userImagePrefix + updatedUser.getImageUrl());

        return ResponseEntity.ok(updatedUser);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
